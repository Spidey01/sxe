#ifndef SXE_DEMOS_QUAD_H
#define SXE_DEMOS_QUAD_H
/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include <sxe/GameEngine.hpp>
#include <sxe/scene/Entity.hpp>
#include <sxe/input/InputFacet.hpp>

#include <string>

namespace demos {

    class Quad
        : public sxe::scene::Entity
    {
      public:

        Quad(sxe::GameEngine& engine);
        ~Quad();

      protected:

      private:

        static const std::string TAG;

        /** Resource URI for our meshes 3D vertex data.
        */
        static const std::string MESH_RESOURCE_PATH;

        sxe::GameEngine& mGameEngine;
        sxe::input::InputFacet mInputFacet;
    };
}

#endif // SXE_DEMOS_QUAD_H
