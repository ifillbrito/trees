#!/bin/bash
dot tree.dot -Tps -o tree.ps;
convert -flatten -density 150 -geometry 100% tree.ps tree.png;