#ifndef SXE_SCENE_ENTITY__HPP
#define SXE_SCENE_ENTITY__HPP
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

#include <sxe/common/stdtypedefs.hpp>

namespace sxe {
  namespace graphics {
    class GraphicsFacet;
  }
  namespace input {
    class InputFacet;
  }
}

namespace sxe { namespace scene {

    class SceneManager;

    /** Defines an entity that may be rendered in a scene.
     *
     */
    class SXE_PUBLIC Entity
        : public common::stdtypedefs<Entity>
    {
      public:
        Entity();
        ~Entity();

        /** @returns the scene manager.
         */
        SceneManager* getSceneManager() const;

        /** Sets the scene manager.
         */
        void setSceneManager(SceneManager* mgr);

        /** Component used for input.
         */
        using input_ptr = std::shared_ptr<input::InputFacet>;

        /** Component used for Input.
         * 
         * @returns the input facet.
         */
        input_ptr getInputFacet() const;

        /** Set the component used for input.
         */
        void setInputFacet(input_ptr input);

        /** Component used for graphics.
         */
        using graphics_ptr = std::shared_ptr<graphics::GraphicsFacet>;

        /** Component used for Graphics.
         * 
         * @returns the graphics facet.
         */
        graphics_ptr getGraphicsFacet() const;

        /** Set the component used for graphics.
         */
        void setGraphicsFacet(graphics_ptr graphics);

      private:
        static const string_type TAG;
        SceneManager* mSceneManager;
        input_ptr mInputFacet;
        graphics_ptr mGraphicsFacet;
    };
} } 

#endif // SXE_SCENE_ENTITY__HPP