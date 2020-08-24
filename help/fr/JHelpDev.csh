#! /bin/csh -f
# SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
#
# csh script to launch JHelpDex with the current directory set
#

java -DHOMEDIR=${PWD} -Djava.io.tmpdir=/tmp -Xmx500m -classpath ".:jhelpdev.jar:lib/jhall.jar:lib/xmlenc.jar" net.sourceforge.jhelpdev.JHelpDevFrame
