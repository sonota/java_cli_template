#!/bin/bash

_get_project_dir() {
  local real_path="$(readlink --canonicalize "$0")"
  (
    cd "$(dirname "$real_path")"
    pwd
  )
}

_build_args(){
  local args="$1"
  shift

  for arg in "$@"; do
    args=$(printf "${args}${ARG_DELIM}${arg}")
  done

  printf "$args"
}

_build() {
  $GRADLE_CMD clean
  $GRADLE_CMD build
}

_exec() {
  local args=$(_build_args "$@")

  $GRADLE_CMD run \
    --quiet \
    -DmainClassName=sample.Main \
    "-Pargs=${args}"
}


# --------------------------------
# Main

CURRENT_DIR=$(pwd)
PROJECT_DIR=$(_get_project_dir)
cd $PROJECT_DIR

source "${PROJECT_DIR}/common.sh"

if [ "$1" = "build" ] ; then
  _build
else
  _exec "${CURRENT_DIR}" "${PROJECT_DIR}" "$@"
fi
