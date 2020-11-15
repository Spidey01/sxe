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

#include <sxe/scene/Camera.hpp>

#include <sxe/logging.hpp>

namespace sxe { namespace scene {

const Camera::string_type Camera::TAG = "TAG";

Camera::Camera()
    // TODO: not hard code.
    : mFieldOfView(45.0f)
    , mAspectRatio(4.0f / 3.0f)
    , mNearClip(0.1f)
    , mFarClip(100.0f)
    , mPosition(0, 0, 2)
    , mFrontDirection(0, 0, -1)
    , mUpDirection(0, 1, 0)
{

}

Camera::mat4 Camera::view() const
{
    return glm::lookAt(mPosition, mPosition + mFrontDirection, mUpDirection);
}

Camera::mat4 Camera::projection() const
{
    // TODO: flag to choose perspective or orthographic.
    return glm::perspective(glm::radians(mFieldOfView), mAspectRatio, mNearClip, mFarClip);
}

} }
