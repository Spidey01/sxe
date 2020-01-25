#ifndef SXE_VK_VULKANINSTANCE__HPP
#define SXE_VK_VULKANINSTANCE__HPP
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

#include <sxe/Game.hpp>
#include <sxe/api.hpp>
#include <vulkan/vulkan.hpp>

namespace sxe { namespace vk {

    /** Core class for initializing a Vulkan instance, for use in SxE.
     */
    class SXE_PUBLIC Vulkan
    {
      public:

        Vulkan() = default;

        /** Our handle to VkInstance and the C++ interface's dispatcher.
         *
         * @param instance the VkInstance to take ownership of.
         * @param loader the vulkan.hpp impl for loading.
         */
        Vulkan(::vk::Instance instance, ::vk::DispatchLoaderDynamic loader);

        /** Destroys the VkInstance and loader.
         */
        ~Vulkan();

        /** Returns our VkInstance.
         */
        ::vk::Instance instance() const;

        /** Returns our loader.
         */
        ::vk::DispatchLoaderDynamic loader() const;

      protected:

      private:

        static const std::string TAG;
        ::vk::Instance mInstance;
        ::vk::DispatchLoaderDynamic mLoader;

    };

} }

#endif // SXE_VK_VULKANINSTANCE__HPP
