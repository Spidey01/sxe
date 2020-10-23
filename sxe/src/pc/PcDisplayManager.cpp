/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include "sxe/pc/PcDisplayManager.hpp"

#if SXE_HAVE_VULKAN
/* Needs to come before glfw3.h, or it won't include support functions for it. */
#include <vulkan/vulkan.hpp>
#endif

#if SXE_HAVE_OPENGL
#include <glbinding/gl/gl.h>
#include <glbinding/glbinding.h>
#include <sxe/gl/ImmediateModeTechnique.hpp>
#include <sxe/gl/OpenGLVersion.hpp>
#endif

#include <GLFW/glfw3.h>
#include <sxe/logging.hpp>

using std::make_shared;
using std::runtime_error;
using sxe::GameEngine;

using namespace sxe::graphics;

namespace sxe { namespace pc {

const PcDisplayManager::string_type PcDisplayManager::TAG = "PcDisplayManager";

PcDisplayManager::PcDisplayManager(const string_type& desired)
    : DisplayManager(TAG)
    , mWindow(nullptr)
{
    Log::xtrace(TAG, "PcDisplayManager(): desired: " + desired);

    DisplayMode mode(desired, false);
    if (!setMode(mode)) {
        throw runtime_error(TAG + ": Can't set displaymode to " + desired);
    }
}


PcDisplayManager::PcDisplayManager()
    : DisplayManager(TAG)
    , mWindow(nullptr)
{
    Log::xtrace(TAG, "PcDisplayManager()");
}


PcDisplayManager::~PcDisplayManager()
{
    Log::xtrace(TAG, "~PcDisplayManager()");
}


bool PcDisplayManager::initialize(GameEngine& engine)
{
    if (!DisplayManager::initialize(engine))
        return false;

    if (!glfwInit()) {
        Log::e(TAG, "glfwInit() failed!");
        return false;
    }

    glfwSetErrorCallback(PcDisplayManager::error_callback);

    using std::to_string;

    Log::d(TAG, "Compiled against GLFW version "
           + to_string(GLFW_VERSION_MAJOR)
           + "." + to_string(GLFW_VERSION_MINOR)
           + "." + to_string(GLFW_VERSION_REVISION));

    int major = 0;
    int minor = 0;
    int revision = 0;
    glfwGetVersion(&major, &minor, &revision);
    Log::d(TAG, "Running against GLFW version "
           + to_string(major)
           + "." + to_string(minor)
           + "." + to_string(revision));

    Log::v(TAG, "glfwGetVersionString(): " + string_type(glfwGetVersionString()));

    return true;
}


bool PcDisplayManager::uninitialize()
{
    Log::xtrace(TAG, "uninitialize()");

    glfwSetErrorCallback(nullptr);

    Log::xtrace(TAG, "glfwTerminate()");
    glfwTerminate();

    return DisplayManager::uninitialize();
}


void PcDisplayManager::update()
{
    DisplayManager::update();
    glfwPollEvents();
}


bool PcDisplayManager::create()
{
    Log::d(TAG, "create()");

    DisplayMode requested = getMode();

    Log::xtrace(TAG, "get the monitor.");
    GLFWmonitor* monitor = isWindowed() ? nullptr : glfwGetPrimaryMonitor();

    int width = requested.width();
    int height = requested.height();
    int bpp = requested.bpp();
    int refresh = requested.refresh();

    if (monitor != nullptr) {
        /* Only relevant to fullscreen mode. */
        Log::xtrace(TAG, "get the monitor's video mode.");
        const GLFWvidmode* native = glfwGetVideoMode(monitor);

        if (width == 0)
            width = native->width;

        if (height == 0)
            height = native->height;

        if (bpp == 0)
            bpp = native->redBits + native->greenBits + native->blueBits;

        if (refresh == 0)
            refresh = native->refreshRate;

        if (bpp > 0) {
            Log::xtrace(TAG, "set hint for color depth.");
            glfwWindowHint(GLFW_RED_BITS, bpp / 3);
            glfwWindowHint(GLFW_GREEN_BITS, bpp / 3);
            glfwWindowHint(GLFW_BLUE_BITS, bpp / 3);
        }
        Log::xtrace(TAG, "set hint for refresh rate.");
        glfwWindowHint(GLFW_REFRESH_RATE, refresh);
    }

    switch (renderingApi()) {
        case RenderingApi::Vulkan:
            Log::i(TAG, "Rendering API is Vulkan.");
            glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
            break;

        case RenderingApi::OpenGLES:
            Log::i(TAG, "Rendering API is OpenGL ES.");

            /*
             * Requesting regular OpenGL and relying on the binding to control
             * which functions are loaded seems to be more reliable than creating
             * a real ES context.
             */

            // glfwWindowHint(GLFW_CLIENT_API, GLFW_OPENGL_ES_API);
            glfwWindowHint(GLFW_CLIENT_API, GLFW_OPENGL_API);
            break;

        default:
            Log::i(TAG, "Rendering API is Unknown.");
            return false;
            break;
    }

    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

    string_type title = getGame()->getName();
    GLFWwindow* shared = nullptr;

    Log::xtrace(TAG, "glfwCreateWindow()");
    // XXX: does GLFW copy the title?
    mWindow = glfwCreateWindow(width, height, title.c_str(), monitor, shared);

    if (mWindow == nullptr) {
        Log::e(TAG, getError());
        return false;
    }

    switch (renderingApi()) {
        case RenderingApi::Vulkan:
            return createVulkanInstance();
            break;

        case RenderingApi::OpenGLES:
            return createOpenGLContext();
            break;

        default:
            return false;
            break;
    }

    return true;
}


void PcDisplayManager::destroy()
{
    Log::d(TAG, "destroy()");

    if (mVulkan)
        mVulkan.reset();

    glfwSetKeyCallback(mWindow, nullptr);

    glfwDestroyWindow(mWindow);
    mWindow = nullptr;
}


bool PcDisplayManager::isCloseRequested() const
{
    return glfwWindowShouldClose(mWindow);
}


bool PcDisplayManager::setMode(DisplayMode mode)
{
    if (!DisplayManager::setMode(mode))
        return false;

    if (mWindow == nullptr || !isInitialized()) {
        return true;
    }

    /* Runtime change. */

    DisplayMode target = getMode();

    GLFWmonitor* monitor = glfwGetWindowMonitor(mWindow);

    int xpos = 0;
    int ypos = 0;
    glfwGetWindowPos(mWindow, &xpos, &ypos);

    glfwSetWindowMonitor(mWindow, monitor,
                         xpos, ypos,
                         target.width(), target.height(),
                         target.refresh());

    return true;
}


GLFWwindow* PcDisplayManager::getWindow() const
{
    if (mWindow == nullptr) {
        Log::w(TAG, "getWindow() called before create!()");
    }

    return mWindow;
}


bool PcDisplayManager::supportsVulkan() const
{
#if SXE_HAVE_VULKAN
    return glfwVulkanSupported() == GLFW_TRUE;
#else
    return false;
#endif
}


bool PcDisplayManager::supportsOpenGL() const
{
#if SXE_HAVE_OPENGL
    return true;
#else
    return false;
#endif
}

PcDisplayManager::string_type PcDisplayManager::getError() const
{
#if GLFW_VERSION_MAJOR >= 3 && GLFW_VERSION_MINOR >= 3

    const char* str;
    int code = glfwGetError(&str);

    string_type r = "glfwGetError(): code: " + std::to_string(code) + " description: ";
    if (str)
        r.append(str);

    return r;

#else

    string_type old = "glfwGetError(): requires >= glfwl 3.3, but SxE was built with ";
    old
        .append(std::to_string(GLFW_VERSION_MAJOR))
        .append(".")
        .append(std::to_string(GLFW_VERSION_MINOR))
        ;

    return old;

#endif
}


void PcDisplayManager::error_callback(int code, const char* description)
{
    string_type str = "glfwGetError(): code: " + std::to_string(code) + " description: ";
    if (description)
        str.append(description);
    Log::e(TAG, str);
}


bool PcDisplayManager::createVulkanInstance()
{
    Log::xtrace(TAG, "createVulkanInstance()");

#if SXE_HAVE_VULKAN

    VkApplicationInfo appInfo = {};

    appInfo.sType = VK_STRUCTURE_TYPE_APPLICATION_INFO;
    appInfo.pApplicationName = getGame()->getName().c_str();
    appInfo.applicationVersion = VK_MAKE_VERSION(0, 0, 0);
    appInfo.pEngineName = "SxE";
    appInfo.engineVersion = VK_MAKE_VERSION(2, 0, 0);
    appInfo.apiVersion = VK_API_VERSION_1_0;

    uint32_t glfwExtensionCount = 0;
    const char** glfwExtensions = glfwGetRequiredInstanceExtensions(&glfwExtensionCount);

    VkInstanceCreateInfo createInfo = {};

    createInfo.sType = VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO;
    createInfo.pApplicationInfo = &appInfo;
    createInfo.enabledExtensionCount = glfwExtensionCount;
    createInfo.ppEnabledExtensionNames = glfwExtensions;
    createInfo.enabledLayerCount = 0;
    createInfo.enabledLayerCount = static_cast<uint32_t>(mVulkanValidationLayers.size());

    /*
     * mVulkanValidationLayers is expected to be read only between create() and
     * destroy(). So we can alias it long enough for instance creation.
     */

    std::vector<const char*> aliases;

    auto vkEnumerateInstanceLayerProperties = (PFN_vkEnumerateInstanceLayerProperties)glfwGetInstanceProcAddress(nullptr, "vkEnumerateInstanceLayerProperties");

    if (createInfo.enabledLayerCount && vkEnumerateInstanceLayerProperties != nullptr) {
        uint32_t bounds;
        vkEnumerateInstanceLayerProperties(&bounds, nullptr);

        std::vector<VkLayerProperties> available(bounds);
        vkEnumerateInstanceLayerProperties(&bounds, available.data());

        auto& layers = mVulkanValidationLayers;
        aliases.reserve(layers.size());

        for (const string_type& str : layers) {
            bool found = false;
            for (const auto& layerProperties : available) {
                if (str == layerProperties.layerName) {
                    found = true;
                    break;
                }
            }

            /* Invalid names cause crashes. */
            if (!found) {
                Log::w(TAG, "Ignoring unavailable validation layer name " + str);
                createInfo.enabledLayerCount -= 1;
                continue;
            }

            Log::d(TAG, "Validation layers += " + str);
            aliases.push_back(str.c_str());
        }
        createInfo.ppEnabledLayerNames = aliases.data();
    }

    auto vkCreateInstance = (PFN_vkCreateInstance)glfwGetInstanceProcAddress(nullptr, "vkCreateInstance");
    if (vkCreateInstance == nullptr) {
        Log::e(TAG, "glfwGetInstanceProcAddress() failed to get vkCreateInstance");
        return false;
    }

    auto vkGetInstanceProcAddr = (PFN_vkGetInstanceProcAddr)glfwGetInstanceProcAddress(nullptr, "vkGetInstanceProcAddr");
    if (vkGetInstanceProcAddr == nullptr) {
        Log::e(TAG, "glfwGetInstanceProcAddress() failed to get vkGetInstanceProcAddr");
        return false;
    }

    VkInstance instance;
    if (vkCreateInstance(&createInfo, nullptr, &instance) != VK_SUCCESS) {
        throw std::runtime_error("VulkanLoader(): vkCreateInstance() failed!");
    }

    ::vk::DispatchLoaderDynamic loader(instance, vkGetInstanceProcAddr);

    mVulkan = std::make_unique<sxe::vk::Vulkan>(instance, loader);

#endif // SXE_HAVE_VULKAN

    return mVulkan != nullptr;
}


bool PcDisplayManager::createOpenGLContext()
{
    Log::xtrace(TAG, "createOpenGLContext()");

#if SXE_HAVE_OPENGL

    Log::xtrace(TAG, "glfwMakeContextCurrent()");
    glfwMakeContextCurrent(mWindow);

    Log::xtrace(TAG, "glbinding::initialize()");
    glbinding::initialize(glfwGetProcAddress);

    /* Debug info about the OpenGL context. */
    sxe::gl::OpenGLVersion ver;

    // XXX: will crash if any of these are nullptr for the platform.
    Log::i(TAG, "OpenGL vendor: " + string_type(ver.vendor()));
    Log::i(TAG, "OpenGL renderer: " + string_type(ver.renderer()));
    Log::i(TAG, "OpenGL version: " + string_type(ver.version()));
    Log::i(TAG, "GLSL version: " + string_type(ver.glslLanguageVersion()));

    Log::d(TAG, "Enabling DrawingTechniques for OpenGL ES.");

    Log::v(TAG, "Enabling ImmediateModeTechnique.");
    mDrawingTechniques.push_back(make_shared<sxe::gl::ImmediateModeTechnique>());

    return true;

#else

    return false;

#endif
}


} }

