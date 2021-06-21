
String getBranchName(branch) {
    branchTemp=sh returnStdout:true ,script:"""echo "$branch" |sed -E "s#origin/##g" """
    if(branchTemp){
        branchTemp=branchTemp.trim()
    }
    return branchTemp
}


    node('master') {
        library 'pipeline-lib'
        
        try {
            // freshStart 
            def freshStart = params.freshStart

            if ( freshStart ) {                
                // remove previous working dir
                print "freshStart... clean working directory ${env.JOB_NAME}"
                sh 'ls -A1|xargs rm -rf' /* clean up our workspace */
            }
            
            def commitId

            def branchTemp
            //branch Name Parsing
            branchTemp = params.branchName
            branch=getBranchName(branchTemp)

            stage('Get Source') {
                git url: "https://github.com/davidhungpill/ktwizapi",
                    credentialsId: 'Git-Credential',
                    branch: "${branch}"
                    commitId = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
            }
            def props = readProperties  file:'devops/jenkins/dockerize.properties'
            def tag = commitId
            def dockerRegistry = props['dockerRegistry']
            def image = props['image']
            def selector = props['selector']
            def namespace = props['namespace']
            def appname = props['appname']
            def apiKey = props['apiKey']
            def projectId = props['projectId']
            
            def mvnSettings = "${env.WORKSPACE}/devops/jenkins/settings.xml"
          
                     
            stage('Maven build') {
                sh "mvn clean package -DskipTests --settings ${mvnSettings}"               
            }

            stage('Build Docker image') {
                docker.withRegistry("${dockerRegistry}", 'cluster-registry-credentials') {
                    sh "docker build -t ${image}:${tag} --build-arg sourceFile=`find target -name '*.jar' | head -n 1` -f devops/jenkins/Dockerfile ."
                    sh "docker push ${image}:${tag}"
                    sh "docker tag ${image}:${tag} ${image}:latest"
                    sh "docker push ${image}:latest"
                }
            }





        } catch(e) {            
            print "Clean up ${env.JOB_NAME} workspace..."
            sh 'ls -A1|xargs rm -rf' /* clean up our workspace */          

            currentBuild.result = "FAILED"
            print " **Error :: " + e.toString()+"**"
            
        }
    }

