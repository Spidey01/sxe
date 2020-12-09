#ifndef SXE_SUM_CHECKSUM__HPP
#define SXE_SUM_CHECKSUM__HPP
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

#include <sxe/api.hpp>
#include <sxe/filesystem.hpp>
#include <sxe/stdheaders.hpp>
#include <sxe/common/stdtypedefs.hpp>

namespace sxe { namespace sum {

    /** Base class for algorithims for use with the Checksum template class.
     */
    class SXE_PUBLIC ChecksumAlgorithim
        : public virtual common::stdtypedefs<ChecksumAlgorithim>
    {
      public:
        ChecksumAlgorithim();

        virtual ~ChecksumAlgorithim();

        /** @brief Updates the rolling checksum.
         * 
         * @param[in] buffer the next input byte[s].
         * @param length byte length of buffer.
         */
        virtual void operator()(const uint8_t* buffer, size_t length) = 0;

        /** @returns the checksum.
         * Recommended format is as a series of hex digits without a leading 0x.
         */
        virtual std::string get() const = 0;
    };

    /** Common implementation for checksum code.
     * 
     * Using the template parameter to provide a ChecksumAlgorithim, it's much
     * easier to implement a checksum function.
     */
    template <class Algorithim>
    class Checksum
    {
      public:
        using algorithim_type = typename Algorithim;
        using string_type = typename algorithim_type::string_type;

        Checksum(Algorithim algorithim)
            : mAlgorithim(algorithim)
        {
        }

        Checksum(std::istream& input, Algorithim algorithim)
            : Checksum(algorithim)
        {
            update(input);
        }

        Checksum(const string_type& input, Algorithim algorithim)
            : Checksum(algorithim)
        {
            update(input);
        }

        Checksum(const uint8_t* buffer, size_t length, Algorithim algorithim)
            : Checksum(algorithim)
        {
            update(buffer, length);
        }

        ~Checksum()
        {
        }

        string_type value() const
        {
            return mAlgorithim.get();
        }

        void update(std::istream& input)
        {
            constexpr size_t BlockSize = 4096;
            char block[BlockSize];

            while (input) {
                memset(block, '\0', BlockSize);
                input.read(block, BlockSize);
                update((uint8_t*)block, input.gcount());
            }
        }

        void update(const string_type& input)
        {
            update((const uint8_t*)input.c_str(), input.size());
        }

        void update(const char* c_str)
        {
            update((const uint8_t*)c_str, std::strlen(c_str));
        }

        void update(const uint8_t* buffer, size_t length)
        {
            mAlgorithim(buffer, length);
        }

      private:
        algorithim_type mAlgorithim;
    };
} }


#endif // SXE_SUM_CHECKSUM__HPP