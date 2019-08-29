node{
    stage("Uptade jenkins"){
        properties([parameters([string(defaultValue: '52.212.111.79', description: 'Please provide IP', name: 'ENVIR', trim: true)])])
        sh "echo Parameter added"
    }
    stage("Install git"){
        sh "ssh ec2-user@${ENVIR} sudo yum install git python-pip -y"
    }
     stage("Remove repo"){
        sh "ssh  ec2-user@${ENVIR} sudo  rm -rf /home/ec2-user/stormpath-flask-sample"
    }
     stage("Remove PID"){
        sh "ssh  ec2-user@${ENVIR} sudo kill $(sudo lsof -i:5000   | awk '{print $2}' | grep [[:digit:]]) 2> /dev/null"
    }
    stage("Pull Repo"){
        sh "ssh ec2-user@${ENVIR} git clone https://github.com/mcalik77/stormpath-flask-sample.git 2> /dev/null"
    }
    stage("Install Requirements"){
        sh "echo Hello"      
    }
    stage("Pip Install"){
        sh "ssh ec2-user@${ENVIR} sudo pip install -r /home/ec2-user/stormpath-flask-sample/requirements.txt"
    }
    stage("Run App"){
        sh "ssh ec2-user@${ENVIR} python   /home/ec2-user/stormpath-flask-sample/app.py"
    }
}