#!/usr/bin/env fish

function do_func
  argparse -n do_func 'h/help' 'i/image=' -- $argv
  or return 1

  if set -lq _flag_help
    echo "buildpacks.fish -i/--image <IMAGENAME:TAG>"
    return
  end

  set -lq _flag_option
  or set -l _flag_option hello:latest

  pack build $_flag_image --builder gcr.io/buildpacks/builder:v1 --publish
end

do_func $argv
