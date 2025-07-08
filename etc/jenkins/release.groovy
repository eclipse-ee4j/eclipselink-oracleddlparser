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
//   DDLPARSER_VERSION       - Oracle DDL Parser version to release
//   NEXT_DDLPARSER_VERSION  - Next Oracle DDL Parser snapshot version to set (e.g. 1.2.4-SNAPSHOT)
//   BRANCH            - Branch to release
//   DRY_RUN           - Do not publish artifacts to OSSRH and code changes to GitHub
//   OVERWRITE         - Allows to overwrite existing version in git and OSSRH staging repositories

// Job internal argumets:
//   GIT_USER_NAME       - Git user name (for commits)
//   GIT_USER_EMAIL      - Git user e-mail (for commits)
//   GIT_BRANCH_RELEASE  - Git branch to release
//   SSH_CREDENTIALS_ID  - Jenkins ID of SSH credentials
//   GPG_CREDENTIALS_ID  - Jenkins ID of GPG credentials (stored as KEYRING variable)

pipeline {

    agent any

    tools {
        jdk 'openjdk-jdk21-latest'
        maven 'apache-maven-latest'
    }

    environment {
        DDLPARSER_DIR = "${WORKSPACE}/oracleddlparser"
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
        // Perform release
        stage('Build and release Oracle DDL Parser') {
            steps {
                git branch: GIT_BRANCH_RELEASE, credentialsId: SSH_CREDENTIALS_ID, url: GIT_REPOSITORY_URL
                sshagent([SSH_CREDENTIALS_ID]) {
                    sh '''
                            etc/jenkins/release.sh "${DDLPARSER_VERSION}" "${NEXT_DDLPARSER_VERSION}" "${DRY_RUN}" "${OVERWRITE}"
                        '''
                }
            }
        }
    }

}
