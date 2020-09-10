#!/bin/bash -e
#
#  Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved.
#
#  This program and the accompanying materials are made available under the
#  terms of the Eclipse Public License v. 2.0 which is available at
#  http://www.eclipse.org/legal/epl-2.0,
#  or the Eclipse Distribution License v. 1.0 which is available at
#  http://www.eclipse.org/org/documents/edl-v10.php.
#
#  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
#
# Arguments:
#  N/A

#
# Arguments:
#  $1 - DDLPARSER_VERSION
#  $2 - NEXT_DDLPARSER_VERSION
#  $3 - DRY_RUN
#  $4 - OVERWRITE

DDLPARSER_VERSION="${1}"
NEXT_DDLPARSER_VERSION="${2}"
DRY_RUN="${3}"
OVERWRITE="${4}"


export MAVEN_SKIP_RC="true"

. etc/scripts/maven.incl.sh
. etc/scripts/nexus.incl.sh

read_version 'DDLPARSER' "${DDLPARSER_DIR}"

if [ -z "${DDLPARSER_RELEASE_VERSION}" ]; then
  echo '-[ Missing required Oracle DDL Parser release version number! ]--------------------------------'
  exit 1
fi

RELEASE_TAG="${DDLPARSER_RELEASE_VERSION}"
RELEASE_BRANCH="${DDLPARSER_RELEASE_VERSION}-RELEASE"

if [ ${DRY_RUN} = 'true' ]; then
  echo '-[ Dry run turned on ]----------------------------------------------------------'
  MVN_DEPLOY_ARGS='install'
  echo '-[ Skipping GitHub branch and tag checks ]--------------------------------------'
else
  MVN_DEPLOY_ARGS='deploy'
  GIT_ORIGIN=`git remote`
  echo '-[ Prepare branch ]-------------------------------------------------------------'
  if [[ -n `git branch -r | grep "${GIT_ORIGIN}/${RELEASE_BRANCH}"` ]]; then
    if [ "${OVERWRITE}" = 'true' ]; then
      echo "${GIT_ORIGIN}/${RELEASE_BRANCH} branch already exists, deleting"
      git push --delete origin "${RELEASE_BRANCH}" && true
    else
      echo "Error: ${GIT_ORIGIN}/${RELEASE_BRANCH} branch already exists"
      exit 1
    fi
  fi
  echo '-[ Release tag cleanup ]--------------------------------------------------------'
  if [[ -n `git ls-remote --tags ${GIT_ORIGIN} | grep "${RELEASE_TAG}"` ]]; then
    if [ "${OVERWRITE}" = 'true' ]; then
      echo "${RELEASE_TAG} tag already exists, deleting"
      git push --delete origin "${RELEASE_TAG}" && true
    else
      echo "Error: ${RELEASE_TAG} tag already exists"
      exit 1
    fi
  fi
fi

# Always delete local branch if exists
git branch --delete "${RELEASE_BRANCH}" && true
git checkout -b ${RELEASE_BRANCH}

# Always delete local tag if exists
git tag --delete "${RELEASE_TAG}" && true

# Read Maven identifiers
read_mvn_id 'DDLPARSER' "${DDLPARSER_DIR}"

# Set Nexus identifiers
DDLPARSER_STAGING_DESC="${DDLPARSER_GROUP_ID}:${DDLPARSER_ARTIFACT_ID}:${DDLPARSER_RELEASE_VERSION}"
DDLPARSER_STAGING_KEY=$(echo ${DDLPARSER_STAGING_DESC} | sed -e 's/\./\\\./g')

# Set release versions
echo '-[ Oracle DDL Parser release version ]--------------------------------------------------------'
set_version 'DDLPARSER' "${DDLPARSER_DIR}" "${DDLPARSER_RELEASE_VERSION}" "${DDLPARSER_GROUP_ID}" "${DDLPARSER_ARTIFACT_ID}" ''

drop_artifacts "${DDLPARSER_STAGING_KEY}" "${DDLPARSER_DIR}"

echo '-[ Deploy artifacts to staging repository ]-----------------------------'
# Verify, sign and deploy release
(cd ${DDLPARSER_DIR} && \
  mvn --no-transfer-progress -U -C -B -V \
      -Poss-release,staging -DskipTests \
      -DstagingDescription="${DDLPARSER_STAGING_DESC}" \
      clean ${MVN_DEPLOY_ARGS})

echo '-[ Tag release ]----------------------------------------------------------------'
git tag "${RELEASE_TAG}" -m "Oracle DDL Parser ${DDLPARSER_RELEASE_VERSION} release"

# Set next release cycle snapshot version
echo '-[ Oracle DDL Parser next snapshot version ]--------------------------------------------------'
set_version 'DDLPARSER' "${DDLPARSER_DIR}" "${DDLPARSER_NEXT_SNAPSHOT}" "${DDLPARSER_GROUP_ID}" "${DDLPARSER_ARTIFACT_ID}" ''

if [ ${DRY_RUN} = 'true' ]; then
  echo '-[ Skipping GitHub update ]-----------------------------------------------------'
else
  echo '-[ Push branch and tag to GitHub ]----------------------------------------------'
  git push origin "${RELEASE_BRANCH}"
  git push origin "${RELEASE_TAG}"
fi
