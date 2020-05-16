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

/// Getting syntax errors if this comes after Vulkan.hpp, on my machine with
/// the newer Vulkan SDK?
#include <sxe/logging.hpp>

#include "sxe/vk/Vulkan.hpp"

using std::runtime_error;
using std::string;
using std::stringstream;
using std::to_string;
using std::vector;

namespace sxe { namespace vk {

const std::string Vulkan::TAG = "Vulkan";

#if SXE_HAVE_VULKAN

    Vulkan::Vulkan(::vk::Instance instance, ::vk::DispatchLoaderDynamic loader)
        : mInstance(instance)
        , mLoader(loader)
        , mPhysicalDevice()
        , mDevice()
    {
        uint32_t deviceCount = 0;
        mLoader.vkEnumeratePhysicalDevices(mInstance, &deviceCount, nullptr);

        if (deviceCount == 0)
            throw runtime_error("VkEnumeratePhysicalDevices returned 0 GPUs with Vulkan support!");

        vector<VkPhysicalDevice> devices(deviceCount);
        mLoader.vkEnumeratePhysicalDevices(mInstance, &deviceCount, devices.data());

        if (devices.empty())
            throw runtime_error("vkEnumeratePhysicalDevices() failed to return any data!");

        /*
         * Spec doesn't define an order, so give ourselves an order of preference.
         */
        VkPhysicalDeviceType deviceTypes[] = {
            // Likely to work.
            VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU,
            VK_PHYSICAL_DEVICE_TYPE_INTEGRATED_GPU,

            // Like VirtualBox?
            VK_PHYSICAL_DEVICE_TYPE_VIRTUAL_GPU,

            // SW rasterizer?
            VK_PHYSICAL_DEVICE_TYPE_CPU,

            // Probably screwed.
            VK_PHYSICAL_DEVICE_TYPE_OTHER,
        };

        for (auto deviceType : deviceTypes) {
            for (const auto& device : devices) {
                if (validateDevice(device, deviceType)) {
                    mPhysicalDevice = device;
                    break;
                }
            }
            if (mPhysicalDevice != ::vk::PhysicalDevice()) {
                Log::i(TAG, "Using " + describeDevice(mPhysicalDevice));
                break;
            }
        }
    }

#endif // SXE_HAVE_VULKAN

    Vulkan::~Vulkan()
    {
#if SXE_HAVE_VULKAN
        Log::d(TAG, "Destroying VkInstance.");
        mInstance.destroy(nullptr, mLoader);
#endif // SXE_HAVE_VULKAN
    }


#if SXE_HAVE_VULKAN

    ::vk::Instance Vulkan::instance() const
    {
        return mInstance;
    }


    ::vk::DispatchLoaderDynamic Vulkan::loader() const
    {
        return mLoader;
    }


    ::vk::PhysicalDevice Vulkan::physicalDevice() const
    {
        return mPhysicalDevice;
    }


    ::vk::Device Vulkan::device() const
    {
        return mDevice;
    }


    string Vulkan::describeDevice() const
    {
        return describeDevice(mPhysicalDevice);
    }


    string Vulkan::describeDevice(::vk::PhysicalDevice device) const
    {
        stringstream ss;

        VkPhysicalDeviceProperties props;
        mLoader.vkGetPhysicalDeviceProperties(device, &props);

        ss
            << "vendorId:" << props.vendorID
            << " deviceId:" << props.deviceID
            << " deviceType:" << props.deviceType
            << " deviceName:" << props.deviceName
            // << " pipelineCacheUUID:" << props.pipelineCacheUUID
            // << " limits:?"
            // << "sparseProperties:?"
            ;

        return ss.str();
    }


    bool Vulkan::validateDevice(::vk::PhysicalDevice device, VkPhysicalDeviceType deviceType)
    {
        Log::xtrace(TAG, "validateDevice(): device: " + describeDevice(device));

        VkPhysicalDeviceProperties properties;
        mLoader.vkGetPhysicalDeviceProperties(device, &properties);

        string rejecting = "validateDevice(): rejecting device: " + describeDevice(device);

        if (properties.deviceType != deviceType) {
            Log::d(TAG, rejecting + " because not deviceType:" + to_string(deviceType));
            return false;
        }

        VkPhysicalDeviceFeatures features;
        mLoader.vkGetPhysicalDeviceFeatures(device, &features);

        /*
         * For now just checking that geometry shaders are available. There's a
         * lot of fields that will be worth applying once nitty gritty shaders
         * are ported over.
         */

        if (!features.geometryShader) {
            Log::d(TAG, rejecting +  " because it does not support geometry shaders.");
            return false;
        }

        /*
         * If there's no graphics queue for graphics, we won't be needing it for rendering.
         */

        uint32_t queueFamilyCount = 0;
        mLoader.vkGetPhysicalDeviceQueueFamilyProperties(device, &queueFamilyCount, nullptr);

        vector<VkQueueFamilyProperties> queues(queueFamilyCount);
        mLoader.vkGetPhysicalDeviceQueueFamilyProperties(device, &queueFamilyCount, queues.data());

        bool hasGraphicsBit = false;
        for (const auto& family : queues) {
            if (family.queueFlags & VK_QUEUE_GRAPHICS_BIT) {
                hasGraphicsBit = true;
                break;
            }
        }
        if (!hasGraphicsBit) {
            Log::d(TAG, rejecting + " because it does not support graphics operations.");
            return false;
        }


        return true;
    }

#endif // SXE_HAVE_VULKAN

} }

