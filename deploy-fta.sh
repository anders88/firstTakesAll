#!/bin/bash

BASEDIR=$(cd -P -- "$(dirname -- "$0")" && pwd -P)

# resolve symlinks
while [ -h "$BASEDIR/$0" ]; do
    DIR=$(dirname -- "$BASEDIR/$0")
    SYM=$(readlink $BASEDIR/$0)
    BASEDIR=$(cd $DIR && cd $(dirname -- "$SYM") && pwd)
done
cd ${BASEDIR}

# --------------------------------------

set -e

yellow() { echo -e "\033[33m$1\033[0m"; }
green() { echo -e "\033[32m$1\033[0m"; }
red() { echo -e "\033[31m$1\033[0m"; }
bold() { echo -e "\033[1;37m$1\033[0m"; }
_fancymessage() {
  echo ""
  green "\033[32m--> $1 \033[0m"
}

info() { bold "$1"; }
ask() { _fancymessage "$1"; }
fail() { red "$1"; exit 1; }

# read -i funker ikke på OSX - derav mer kronglete løsning :(
_readWithDefault() {
    local default=$1
    read answer
    if [ "$answer" = "" ]; then
         answer="$default"
    fi
    echo $answer
}

info "BYGGER"
mvn clean install

yellow ""
yellow "  deployer fta"
yellow ""

DEFAULT_JAR=`find . -name *-jar-with-dependencies.jar`
DEFAULT_WAR=`find . -name *.war`
DEFAULT_VERSION=`date +%Y%m%d%H%M%S`-SNAPSHOT

ask "Hvor ligger jar-filen? [$DEFAULT_JAR]"
JAR=$(_readWithDefault $DEFAULT_JAR)

ask "Hvor ligger war-filen? [$DEFAULT_WAR]"
WAR=$(_readWithDefault $DEFAULT_WAR)

ask "Hvilken versjon? [$DEFAULT_VERSION]"
VERSION=$(_readWithDefault $DEFAULT_VERSION)


if [ ! -f $JAR ]; then
	fail "Fant ikke $JAR :("
fi


HOST="www.anderssandbox.com"
BASE="/home/anders/web/fta"

info "Deployer til boksen på $HOST:$BASE med versjon $VERSION med jar $JAR og war $WAR"

ssh anders@$HOST "mkdir -p $BASE/$VERSION"
info "Laster opp jar"
scp $JAR anders@$HOST:$BASE/$VERSION/fta.jar
info "Laster opp war"
scp $WAR anders@$HOST:$BASE/$VERSION/fta.war
ssh anders@$HOST "ln -s -f $VERSION -T $BASE/current"
ssh anders@$HOST "$BASE/fta.sh stop"
ssh anders@$HOST "$BASE/fta.sh start"

