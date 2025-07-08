// Copyright (c) 2020, 2025 Oracle and/or its affiliates. All rights reserved.
//
// This program and the accompanying materials are made available under the
// terms of the Eclipse Distribution License v. 1.0, which is available at
// http://www.eclipse.org/org/documents/edl-v10.php.
// or the Eclipse Distribution License v. 1.0 which is available at
// http://www.eclipse.org/org/documents/edl-v10.php.
//
//  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause

// Job input parameters:
//   GIT_BRANCH_RELEASE   - Branch to release

// Job internal argumets:
//   GIT_USER_NAME       - Git user name (for commits)
//   GIT_USER_EMAIL      - Git user e-mail (for commits)
//   SSH_CREDENTIALS_ID  - Jenkins ID of SSH credentials
//   GPG_CREDENTIALS_ID  - Jenkins ID of GPG credentials (stored as KEYRING variable)

pipeline {

    agent any

    tools {
        jdk 'openjdk-jdk21-latest'
        maven 'apache-maven-latest'
    }

    environment {
        DDLPARSER_DIR="${WORKSPACE}/oracleddlparser"
    }

    stages {
        // Initialize build environment
        stage('Init') {
            steps {
                git branch: GIT_BRANCH_RELEASE, credentialsId: SSH_CREDENTIALS_ID, url: GIT_REPOSITORY_URL
                withCredentials([file(credentialsId: 'secret-subkeys.asc', variable: 'KEYRING')]) {
                    sh label: '', script: '''
                        gpg --batch --import "${KEYRING}"
                        for fpr in $(gpg --list-keys --with-colons  | awk -F: \'/fpr:/ {print $10}\' | sort -u);
                        do
                            echo -e "5\\ny\\n" |  gpg --batch --command-fd 0 --expert --edit-key $fpr trust;
                        done'''
                }
                // Git configuration
                sh '''
                    git config --global user.name "${GIT_USER_NAME}"
                    git config --global user.email "${GIT_USER_EMAIL}"
                '''
            }
        }
        // Build
        stage('Build') {
            steps {
                sshagent([SSH_CREDENTIALS_ID]) {
                    sh '''
                        etc/jenkins/build.sh
                    '''
                }
            }
        }
        // Publish to snapshots
        stage('Publish to snapshots') {
            steps {
                sh '''
                    etc/jenkins/publish_snapshots.sh
                '''
            }
        }
    }
}
