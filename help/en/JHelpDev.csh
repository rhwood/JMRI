#! /bin/csh -f
# SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
#
# csh script to launch JHelpDev with the current directory set
#

java -DHOMEDIR=${PWD} -Djava.io.tmpdir=/tmp -Xmx2000m -classpath ".:jhelpdev.jar:jars/jhall.jar:jars/xmlenc.jar" net.sourceforge.jhelpdev.JHelpDevFrame
