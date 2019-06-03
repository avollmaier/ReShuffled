#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=avr-gcc
CCC=avr-g++
CXX=avr-g++
FC=gfortran
AS=avr-as

# Macros
CND_PLATFORM=GNU-AVR-Linux
CND_DLIB_EXT=so
CND_CONF=Release
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/src/app.o \
	${OBJECTDIR}/src/main.o \
	${OBJECTDIR}/src/mon.o \
	${OBJECTDIR}/src/sys.o


# C Compiler Flags
CFLAGS=-Wall -Os -mmcu=atmega324p

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/sure.elf

${CND_DISTDIR}/sure.elf: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}
	${LINK.c} -o ${CND_DISTDIR}/sure.elf ${OBJECTFILES} ${LDLIBSOPTIONS} -mmcu=atmega324p -Wl,-Map=${CND_DISTDIR}/asuro.map,--cref

${OBJECTDIR}/src/app.o: src/app.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.c) -O2 -Wall -s -DPROJECT_ATMEGA324P -std=c99 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/app.o src/app.c

${OBJECTDIR}/src/main.o: src/main.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.c) -O2 -Wall -s -DPROJECT_ATMEGA324P -std=c99 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/main.o src/main.c

${OBJECTDIR}/src/mon.o: src/mon.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.c) -O2 -Wall -s -DPROJECT_ATMEGA324P -std=c99 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/mon.o src/mon.c

${OBJECTDIR}/src/sys.o: src/sys.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.c) -O2 -Wall -s -DPROJECT_ATMEGA324P -std=c99 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/sys.o src/sys.c

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/sure.elf

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
