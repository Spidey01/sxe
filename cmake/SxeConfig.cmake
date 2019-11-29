get_filename_component(SELF_DIR "${CMAKE_CURRENT_LIST_FILE}" PATH)

# Allows find_package(SxE) to work like:
#
#    find_package(SxeCore)
#    find_package(Sxe...)
#
# But doesn't let you target_link_libraries(tgt SxE) instead of
# target_link_libraries(tgt SxeCore SxE...)

include(${SELF_DIR}/SxeCoreConfig.cmake)
