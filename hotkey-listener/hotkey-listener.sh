#!/bin/sh

readonly SERVER_BASE='localhost:5000/api/v1'
readonly COMMAND_NEXT_PAGE="$SERVER_BASE/next-page"
readonly COMMAND_CURRENT_PAGE="$SERVER_BASE/current-page"
readonly COMMAND_PREV_PAGE="$SERVER_BASE/previous-page"

func_usage() {
(echo "
Usage:
hotkey-listener.sh -h | hotkey-listener.sh --help

  Prints this help and exits. Configure shortcuts for this script in 
  the system settings to translate custom hotkeys for the curl calls.

hotkey-listener.sh COMMAND

  COMMAND is one of the following

  -n, --next          calls endpoint for next page command

  -p, --previous      calls endpoint for previous page command

  -c, --current       calls endpoint for current page command
  ")
}

ERROR=0

calling_endpoint() {
    url=$1
    response=$(curl -w "%{http_code}" "$url" | xclip -selection clipboard -target image/png -i)

    if [ "$response" -ge 200 ] && [ "$response" -lt 300 ]; then
        echo "- HTTP status code is OK (2xx): $response"
    else
        echo "- HTTP status code is not OK: $response"
        ERROR=2
    fi
}

if [ "$#" -eq 1 ]
then
    if [ "$1" = "-h" ] || [ "$1" = "--help" ]
    then
        func_usage
    elif [ "$1" = "-n" ] || [ "$1" = "--next" ]
    then
        echo "- turning to next page - calling $COMMAND_NEXT_PAGE"
        calling_endpoint $COMMAND_NEXT_PAGE
    elif [ "$1" = "-c" ] || [ "$1" = "--current" ]
    then
        echo "- getting current page - calling $COMMAND_CURRENT_PAGE"
        calling_endpoint $COMMAND_CURRENT_PAGE
    elif [ "$1" = "-p" ] || [ "$1" = "--previous" ]
    then
        echo "- turning to previous page - calling $COMMAND_PREV_PAGE"
        calling_endpoint $COMMAND_PREV_PAGE
    else
        ERROR=1
    fi
fi

case $ERROR in 
    0) ;;
    1)echo "- Error: wrong param count" 1>&2;;
    2)echo "- Error: backend gave invalid response code" 1>&2;;
    *)echo "- Error: " 1>&2;;
esac

if [ $ERROR -gt 0 ]
then 
    func_usage >&2
fi
exit $ERROR

