ARG_DELIM="\x1f"

# MVN_CMD="/path/to/apache-maven-x.x.x/bin/mvn"
MVN_CMD="mvn"

# GRADLE_CMD="/path/to/gradle-x.x/bin/gradle"
GRADLE_CMD="gradle"

_build_args(){
  local args="$1"
  shift

  for arg in "$@"; do
    args=$(printf "${args}${ARG_DELIM}${arg}")
  done

  printf "$args"
}

_build_mvn() {
  $MVN_CMD clean
  $MVN_CMD compile
}

_build_gradle() {
  $GRADLE_CMD clean
  $GRADLE_CMD build
}
