#! /bin/sh
# SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
#
# Script to start a JMRI class directly, e.g during development
#
# Calls the script by the same name in the parent directory. See that script
# for further documentation.

cd ..
 ./runtest.csh $@
