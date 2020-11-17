#ifndef SXE_GL_VERTEXBUFFERTECHNIQUE__HPP
#define SXE_GL_VERTEXBUFFERTECHNIQUE__HPP
/*-
 * Copyright (c) 2012-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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
#include <sxe/gl/Program.hpp>
#include <sxe/gl/Shader.hpp>
#include <sxe/gl/VertexArrayObject.hpp>
#include <sxe/gl/VertexBufferObject.hpp>
#include <sxe/graphics/DrawingTechnique.hpp>
#include <sxe/graphics/GraphicsFacet.hpp>
#include <sxe/graphics/ShaderType.hpp>

namespace sxe
{
    namespace resource
    {
        class ResourceManager;
    }
} // namespace sxe

namespace sxe { namespace gl {

    /** Technique for rendering via Vertex Buffer Objects (VBOs).
     * 
     */
    class SXE_PUBLIC VertexBufferTechnique
        : public graphics::DrawingTechnique
    {
      public:
        /** Create the VBO based drawing technique.
         * 
         * @param resources manager to load our shaders.
         */
        VertexBufferTechnique(sxe::resource::ResourceManager& resources);
        virtual ~VertexBufferTechnique();
        void frameStarted() override;
        void draw(graphics::GraphicsFacet& facet) override;
        void frameEnded() override;

      private:
        static const string_type TAG;
        Program mProgram;
        VertexBufferObject mVBO; // until pools are operational.
        size_t mNextOffset;
        Program::string_type mPositionName;
        Program::AttributeLocation mPositionIndex;
        Program::string_type mColorName;
        Program::AttributeLocation mColorIndex;
        Program::string_type mTransformName;
        Program::UniformLocation mTransformIndex;
        std::vector<Shader::ShaderId> mShaderIds;

        /** Loads the shader, adds its id to mShaderIds.
         * 
         * @param resources manager to load our shaders.
         * @param path the resource path to the shader source.
         * @param type the shader type.
         * @returns the shader.
         */
        Shader::unique_ptr loadShader(sxe::resource::ResourceManager& resources, const path_type& path, graphics::ShaderType type);
    };

} }

#endif // SXE_GL_VERTEXBUFFERTECHNIQUE__HPP