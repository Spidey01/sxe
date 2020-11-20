#ifndef SXE_GRAPHICS_SYSTEMMEMORY__HPP
#define SXE_GRAPHICS_SYSTEMMEMORY__HPP
/*-
 * Copyright (c) 2020-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the
 * use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 *	1. The origin of this software must not be misrepresented; you must
 *	   not claim that you wrote the original software. If you use this
 *	   software in a product, an acknowledgment in the product
 *	   documentation would be appreciated but is not required.
 *
 *	2. Altered source versions must be plainly marked as such, and must
 *	   not be misrepresented as being the original software.
 *
 *	3. This notice may not be removed or altered from any source
 *	   distribution.
 */

#include <sxe/graphics/MemoryBuffer.hpp>

namespace sxe { namespace graphics {

    /** A MemoryBuffer using system memory.
     * 
     * The same sxe::graphics::MemoryBuffers interface is used for both buffers
     * allocated in system memory, and for buffers allocated in graphics memory
     * by the backend (e.g. OpenGL) rendering engine.
     */
    class SXE_PUBLIC SystemMemory
        : public MemoryBuffer
    {
      public:
        /** Creates an empty buffer.
         * 
         * Equal to SystemMemory((uintptr_t)this, Log::TEST, "SystemMemory");
         * Note that this may give collisions, as pointers are likely >
         * buffer_id.
         */
        SystemMemory();

        /** Creates a SystemMemory buffer more friendly to extending.
         * 
         * @param theId initial value of id().
         * @param level used for logging.
         * @param tag used for logging.
         */
        SystemMemory(buffer_id theId, int level, const string_type& tag);

        virtual ~SystemMemory();

        void bind() override;
        void allocate(size_type size, const void* pointer) override;
        void buffer(difference_type offset, size_type size, const void* data) override;
        void* map(MapType access) override;
        bool unmap() override;

      private:
        uint8_t* mBuffer;
    };
} }

#endif // SXE_GRAPHICS_SYSTEMMEMORY__HPP
