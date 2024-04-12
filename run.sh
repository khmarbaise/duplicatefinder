#!/bin/bash
echo "Enter an image path to find duplicates in (e.g. /home/username/images):"
read path

if [ ! -d "$path" ]; then
    echo "Error: Directory '$path' does not exist."
    exit 1
fi

java -jar target/duplicate-finder.jar "$path"
