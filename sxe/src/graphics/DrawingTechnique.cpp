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

#include <sxe/graphics/DrawingTechnique.hpp>

#include <sxe/logging.hpp>

using std::to_string;

namespace sxe { namespace graphics {

    using string_type = DrawingTechnique::string_type;

    const string_type DrawingTechnique::TAG = "DrawingTechnique";

    DrawingTechnique::DrawingTechnique(const string_type& name, const string_type& comment)
        : mName(name)
        , mComment(comment)
        , mLogLevel(Log::TEST)
        , mClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    {
        Log::log(mLogLevel, TAG, "DrawingTechnique(): name: " + mName + " comment: " + mComment);
    }

    DrawingTechnique::~DrawingTechnique()
    {
        Log::log(mLogLevel, TAG, "~DrawingTechnique(): name: " + mName + " comment: " + mComment);
    }

    const string_type& DrawingTechnique::name() const
    {
        return mName;
    }

    const string_type& DrawingTechnique::comment() const
    {
        return mComment;
    }

    int DrawingTechnique::logLevel() const
    {
        return mLogLevel;
    }

    void DrawingTechnique::buffer(GraphicsFacet& facet)
    {
        Log::log(mLogLevel, TAG, "buffer(): name(): " + mName);
        (void)facet;
    }

    void DrawingTechnique::unbuffer(GraphicsFacet& facet)
    {
        Log::log(mLogLevel, TAG, "unbuffer(): name(): " + mName);
        (void)facet;
    }

    void DrawingTechnique::logLevel(int level)
    {
        mLogLevel;
    }

    void DrawingTechnique::frameStarted()
    {
        Log::log(mLogLevel, TAG, "frameStarted(): name(): " + mName);
    }

    void DrawingTechnique::draw(GraphicsFacet& facet)
    {
        Log::log(mLogLevel, TAG, "draw(): name(): " + mName);

        facet.onDraw();
    }

    void DrawingTechnique::frameEnded()
    {
        Log::log(mLogLevel, TAG, "frameEnded(): name(): " + mName);
    }

    Vertex::color_type DrawingTechnique::clearColor() const
    {
        return mClearColor;
    }

    /** Get the color that should be used for clearing the frame.
       */
    void DrawingTechnique::clearColor(Vertex::color_type color)
    {
        std::stringstream ss;

        ss
            << std::hex << std::setw(2)
            << "old: #" << mClearColor.r << mClearColor.g << mClearColor.b << mClearColor.a
            << " "
            << "new: #" << color.r << color.g << color.b << color.a;

        Log::log(mLogLevel, TAG, "clearColor(): " + ss.str());

        mClearColor = color;
    }
} }
