#!/bin/bash

mongoimport --db memesdb --collection memes --drop --jsonArray --file ./memes-data.json