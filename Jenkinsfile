pipeline {
    agent any
    stages {
        stage("Read from Maven POM"){
            steps{
                script{
                    projectArtifactId = readMavenPom().getArtifactId()
                    projectVersion = readMavenPom().getModelVersion()
                }
                echo "Building ${projectArtifactId}:${projectVersion}"
            }
        }
//         stage("Test"){
//             steps {
//                 bat "mvn -version"
//                 bat "mvn test"
//             }
//         }
        stage("Build JAR file"){
            steps{
                sh script: "mvn install -Dmaven.test.skip=true"
            }
        }
        stage("Build Docker image"){
            steps {
                echo "Building service image and pushing it to DockerHub"
                    withCredentials([usernamePassword(credentialsId: 'Docker', usernameVariable: "dockerLogin",
                        passwordVariable: "dockerPassword"),string(credentialsId: 'DecryptPassword',variable: "password")]) {
                            sh script: "docker login -u ${dockerLogin} -p ${dockerPassword}"
                            sh script: "docker image build --build-arg PASSWORD=${password} -t ${dockerLogin}/auction ."
                            sh script: "docker push ${dockerLogin}/auction"
                        }
                echo "Building image and pushing it to DockerHub is successful done"
            }
        }
                stage("Deploy On AWS EC2 Instance"){
                    steps{
                        withCredentials([string(credentialsId: 'DecryptPassword',variable: "password"),
                                        usernamePassword(credentialsId: 'Docker', usernameVariable: "dockerLogin",
                                            passwordVariable: "dockerPassword")]){
                         script{
                        def remote = [:]
                            remote.user = 'ec2-user'
                            remote.host = 'ec2-3-70-24-74.eu-central-1.compute.amazonaws.com'
                            remote.name = 'ec2-user'
                            remote.identityFile = '~/home/ec2-user/keypair/Alexandru.pem'
                            remote.allowAnyHosts = 'true'
                            //sshCommand remote: remote, command: "docker login -u ${dockerLogin} -p ${dockerPassword}"
//                             sshCommand remote: remote, command: 'docker container kill $(docker ps -a -q)'
//                             sshCommand remote: remote, command: 'docker rm $(docker ps -a -q)'
                            sshCommand remote: remote, command: 'docker rmi $(docker images -q)'
                            sshCommand remote: remote, command: "docker login | docker pull arthur2104/auction"
                            sshCommand remote: remote, command: 'docker container run -e "PASSWORD=${password}" -d -p 80:8282 --name auction arthur2104/auction'
                            sshCommand remote: remote, command: "exit"
                        }
                        timeout(time: 90, unit: 'SECONDS') {
                        waitUntil(initialRecurrencePeriod: 2000) {
                            script {
                                def result =
                                sh script: "curl --silent --output /dev/null ec2-18-184-137-30.eu-central-1.compute.amazonaws.com/api/v1/categories",
                                returnStatus: true
                                return (result == 0)
                                }
                            }
                        }
                        echo "Server is up"
                    }
                }}
//         stage("Deploy Locally"){
//             steps{
//                 bat "docker-compose --file docker-compose.yml up --detach"
//                 timeout(time: 90, unit: 'SECONDS') {
//                 waitUntil(initialRecurrencePeriod: 2000) {
//                     script {
//                         def result =
//                         sh script: "curl --silent --output /dev/null http://localhost:8282/api/v1/categories",
//                         returnStatus: true
//                         return (result == 0)
//                         }
//                     }
//                 }
//                 echo "Server is up"
//             }
//         }
//         stage("Newman Test"){
//             steps{
//                 echo "Starting Newman Test"
//                 bat "newman run --disable-unicode https://www.getpostman.com/collections/345d1665e5bdd9ca448e"
//             }
//         }
//         stage("JMeter Loading Test"){
//             steps{
//             echo "Starting the JMeter Loading Test"
//             bat "jmeter -jjmeter.save.saveservice.output_format.xml -n -t D:/RestaurantAPI.jmx -l D:/report.jtl"
//             }
//         }
    }
    post {
        always {
            cleanWs()
        }
    }
}
