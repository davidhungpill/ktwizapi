
String getBranchName(branch) {
    branchTemp=sh returnStdout:true ,script:"""echo "$branch" |sed -E "s#origin/##g" """
    if(branchTemp){
        branchTemp=branchTemp.trim()
    }
    return branchTemp
}


    node('master') {
        
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
				print "### getting source started ###")
                git url: "https://github.com/davidhungpill/ktwizapi",
                    credentialsId: 'Git-Credential',
                    branch: "${branch}"
                    commitId = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
				print "### getting source completed ###")
            }
            def dockerRegistry = 'https://hub.docker.com/'
            def image = 'davidhungpill/ktwizapi'
			def tag = 'latest'
			
            def mvnSettings = "${env.WORKSPACE}/devops/jenkins/settings.xml"
          
                     
            stage('Maven build') {
				print "### maven build start ###")
                sh "mvn clean package -DskipTests --settings ${mvnSettings}"               
				print "### maven build completed ###")
            }

            stage('Build Docker image') {
				print "### dockerfile build start ###")
                docker.withRegistry("${dockerRegistry}", 'dockerhub-credential') {
                    sh "docker build -t ${image}:${tag} --build-arg sourceFile=`find target -name '*.jar' | head -n 1` -f devops/jenkins/Dockerfile ."
                    sh "docker push ${image}:${tag}"
                    sh "docker tag ${image}:${tag} ${image}:latest"
                    sh "docker push ${image}:latest"
                }
				print "### dockerfile build completed ###")
            }





        } catch(e) {            
            print "Clean up ${env.JOB_NAME} workspace..."
            sh 'ls -A1|xargs rm -rf' /* clean up our workspace */          

            currentBuild.result = "FAILED"
            print " **Error :: " + e.toString()+"**"
            
        }
    }

