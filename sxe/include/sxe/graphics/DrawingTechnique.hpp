#ifndef SXE_GRAPHICS_DRAWINGTECHNIQUE__HPP
#define SXE_GRAPHICS_DRAWINGTECHNIQUE__HPP
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

#include <sxe/api.hpp>
#include <sxe/common/stdtypedefs.hpp>
#include <sxe/graphics/FrameListener.hpp>
#include <sxe/graphics/GraphicsFacet.hpp>
#include <sxe/graphics/Vertex.hpp>
#include <sxe/graphics/stdmathtypes.hpp>

namespace sxe { namespace graphics {

  /** Interface to drawing techniques.
   * 
   * Defines an interface for the SceneManager to draw the scene based on
   * the DisplayManager's provided RenderingApi.
   * 
   * In SxE 2.0 this was renamed "DrawingTechnique" from
   * "GraphicsTechnique".
   */
  class SXE_PUBLIC DrawingTechnique
      : public common::stdtypedefs<DrawingTechnique>
      , public graphics::stdmathtypes
      , public virtual FrameListener
  {
    public:
      DrawingTechnique(const string_type& name, const string_type& comment);
      virtual ~DrawingTechnique();

      /** Returns the name of this technique.
       */
      const string_type& name() const;

      /** Returns the comment about this technique.
       */
      const string_type& comment() const;

      /** Get the log level.
       * 
       * By default the level is Log::TEST.
       */
      int logLevel() const;

      /** Set the log level.
       */
      void logLevel(int level);

      /** Called to prepare facet for draw().
       */
      virtual void buffer(GraphicsFacet& facet);

      /** Call to release resources associated with the facet.
       */
      virtual void unbuffer(GraphicsFacet& facet);

      /** Called at the start of every frame by SceneManager.
       * 
       * Default implementation simple logs that name() started the frame.
       */
      virtual void frameStarted();

      /** Called to draw
       * 
       * Default implementation simple logs that name() started the draw.
       */
      virtual void draw(GraphicsFacet& facet);

      /** Called at the end of every frame by SceneManager.
       * 
       * Default implementation simple logs that name() started the frame.
       */
      virtual void frameEnded();

      /** Get the color that should be used for clearing the frame.
       * 
       * @returns the color; default is black.
       */
      Vertex::color_type clearColor() const;

      /** Get the color that should be used for clearing the frame.
       */
      void clearColor(Vertex::color_type color);

    protected:
    private:
      static const string_type TAG;
      string_type mName;
      string_type mComment;
      int mLogLevel;
      Vertex::color_type mClearColor;
    };

  } }

  #endif // SXE_GRAPHICS_DRAWINGTECHNIQUE__HPP