# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :
CMAKE_MINIMUM_REQUIRED(VERSION 3.13 FATAL_ERROR)

include(GNUInstallDirs)

find_package(Doxygen)

if (DOXYGEN_FOUND AND BUILD_DOCS)
    set(DOXYGEN_PROJECT_BRIEF "My experimentation in developing a game engine.")
    set(DOXYGEN_PROJECT_NAME "SxE")
    set(DOXYGEN_EXTRACT_ALL YES)
    set(DOXYGEN_STRIP_FROM_INC_PATH ${PROJECT_SOURCE_DIR}/sxe/include)
    set(DOXYGEN_USE_MDFILE_AS_MAINPAGE "${PROJECT_SOURCE_DIR}/README.md")

    doxygen_add_docs(docs
        ${PROJECT_SOURCE_DIR}/sxe/
        ${PROJECT_SOURCE_DIR}/NOTICE
        ${PROJECT_SOURCE_DIR}/COPYING
        ${PROJECT_SOURCE_DIR}/README.md
        Build.md
        Linux.md
        Windows.md
        Settings.md
        ALL)

    install(DIRECTORY ${CMAKE_CURRENT_BINARY_DIR}/html
            DESTINATION ${CMAKE_INSTALL_DOCDIR})

endif (DOXYGEN_FOUND AND BUILD_DOCS)
