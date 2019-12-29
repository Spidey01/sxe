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

#include "sxe/core/input/InputCode.hpp"

#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using std::to_string;

namespace sxe { namespace core { namespace input {

const string InputCode::TAG = "InputCode";

InputCode InputCode::fromChar(char ch)
{
    switch (ch) {
        case '0': return InputCode(IC_0, '0', '0');
        case '1': return InputCode(IC_1, '1', '1');
        case '2': return InputCode(IC_2, '2', '2');
        case '3': return InputCode(IC_3, '3', '3');
        case '4': return InputCode(IC_4, '4', '4');
        case '5': return InputCode(IC_5, '5', '5');
        case '6': return InputCode(IC_6, '6', '6');
        case '7': return InputCode(IC_7, '7', '7');
        case '8': return InputCode(IC_8, '8', '8');
        case '9': return InputCode(IC_9, '9', '9');

        case 'A': case 'a': return InputCode(IC_A, 'A', 'a');
        case 'B': case 'b': return InputCode(IC_B, 'B', 'b');
        case 'C': case 'c': return InputCode(IC_C, 'C', 'c');
        case 'D': case 'd': return InputCode(IC_D, 'D', 'd');
        case 'E': case 'e': return InputCode(IC_E, 'E', 'e');
        case 'F': case 'f': return InputCode(IC_F, 'F', 'f');
        case 'G': case 'g': return InputCode(IC_G, 'G', 'g');
        case 'H': case 'h': return InputCode(IC_H, 'H', 'h');
        case 'I': case 'i': return InputCode(IC_I, 'I', 'i');
        case 'J': case 'j': return InputCode(IC_J, 'J', 'j');
        case 'K': case 'k': return InputCode(IC_K, 'K', 'k');
        case 'L': case 'l': return InputCode(IC_L, 'L', 'l');
        case 'M': case 'm': return InputCode(IC_M, 'M', 'm');
        case 'N': case 'n': return InputCode(IC_N, 'N', 'n');
        case 'O': case 'o': return InputCode(IC_O, 'O', 'o');
        case 'P': case 'p': return InputCode(IC_P, 'P', 'p');
        case 'Q': case 'q': return InputCode(IC_Q, 'Q', 'q');
        case 'R': case 'r': return InputCode(IC_R, 'R', 'r');
        case 'S': case 's': return InputCode(IC_S, 'S', 's');
        case 'T': case 't': return InputCode(IC_T, 'T', 't');
        case 'U': case 'u': return InputCode(IC_U, 'U', 'u');
        case 'V': case 'v': return InputCode(IC_V, 'V', 'v');
        case 'W': case 'w': return InputCode(IC_W, 'W', 'w');
        case 'X': case 'x': return InputCode(IC_X, 'X', 'x');
        case 'Y': case 'y': return InputCode(IC_Y, 'Y', 'y');
        case 'Z': case 'z': return InputCode(IC_Z, 'Z', 'z');

        case '&': return InputCode(IC_AMPERSAND, '&', '&');
        case '\'': return InputCode(IC_APOSTROPHE, '\'', '\'');
        case '*': return InputCode(IC_ASTERISK, '*', '*');
        case '@': return InputCode(IC_AT_SIGN, '@', '@');
        case '\\': return InputCode(IC_BACKSLASH, '\\', '\\');
        case '\b': return InputCode(IC_BACKSPACE, '\b', '\b');
        case '^': return InputCode(IC_CARET, '^', '^');
        case ':': return InputCode(IC_COLON, ':', ':');
        case ',': return InputCode(IC_COMMA, ',', ',');
        case '$': return InputCode(IC_DOLLAR_SIGN, '$', '$');
        case '"': return InputCode(IC_DOUBLE_QUOTE, '"', '"');
        case '\n': return InputCode(IC_ENTER, '\n', '\n');
        case '=': return InputCode(IC_EQUAL_SIGN, '=', '=');
        case '!': return InputCode(IC_EXCLAMATION_MARK, '!', '!');
        case '`': return InputCode(IC_GRAVE, '`', '`');
        case '>': return InputCode(IC_GREATER_THAN_SIGN, '>', '>');
        case '-': return InputCode(IC_HYPHEN_MINUS, '-', '-');
        case '{': return InputCode(IC_LEFT_CURLY_BRACE, '{', '{');
        case '(': return InputCode(IC_LEFT_PAREN, '(', '(');
        case '[': return InputCode(IC_LEFT_SQUARE_BRACKET, '[', '[');
        case '<': return InputCode(IC_LESS_THAN_SIGN, '<', '<');
        case '#': return InputCode(IC_NUMBER_SIGN, '#', '#');
        case '%': return InputCode(IC_PERCENT_SIGN, '%', '%');
        case '.': return InputCode(IC_PERIOD, '.', '.');
        case '|': return InputCode(IC_PIPE, '|', '|');
        case '+': return InputCode(IC_PLUS_SIGN, '+', '+');
        case '?': return InputCode(IC_QUESTION_MARK, '?', '?');
        case '}': return InputCode(IC_RIGHT_CURLY_BRACE, '}', '}');
        case ')': return InputCode(IC_RIGHT_PAREN, ')', ')');
        case ']': return InputCode(IC_RIGHT_SQUARE_BRACKET, ']', ']');
        case ';': return InputCode(IC_SEMICOLON, ';', ';');
        case '/': return InputCode(IC_SLASH, '/', '/');
        case ' ': return InputCode(IC_SPACE, ' ', ' ');
        case '\t': return InputCode(IC_TAB, '\t', '\t');
        case '~': return InputCode(IC_TILDE, '~', '~');
        case '_': return InputCode(IC_UNDERSCORE, '_', '_');

        case '\0':
        default:
            return InputCode(IC_UNKNOWN, ch, ch);
    }
}


char InputCode::symbol() const
{
    return mUpperCase;
}


char InputCode::upper() const
{
    return mUpperCase;
}


char InputCode::lower() const
{
    return mLowerCase;
}


int InputCode::code() const
{
    return mOrdinal;
}


std::string InputCode::dump() const
{
    std::ostringstream debug;

    debug << "InputCode:"
        << " code => " << code()
        << ", symbol() => " << symbol()
        << ", upper() => " << upper()
        << ", lower() => " << lower()
        ;

    return debug.str();
}


bool InputCode::operator== (int value) const
{
    return code() == value;
}


bool InputCode::operator!= (int value) const
{
    return code() != value;
}


InputCode::InputCode()
    : InputCode(IC_UNKNOWN, '\0', '\0')
{
}


InputCode::InputCode(int ordinal)
    : mOrdinal(ordinal)
    , mUpperCase(upperForOrdinal(mOrdinal))
    , mLowerCase(lowerForOrdinal(mOrdinal))
{
}


InputCode::InputCode(int ordinal, char upper, char lower)
    : mOrdinal(ordinal)
    , mUpperCase(upper)
    , mLowerCase(lower)
{
}


char InputCode::lowerForOrdinal(int ic)
{
    switch (ic) {
        case IC_TILDE: return '~';
        case IC_GRAVE: return '`';
        case IC_EXCLAMATION_MARK: return '!';
        case IC_1: return '1';
        case IC_AT_SIGN: return '@';
        case IC_2: return '2';
        case IC_NUMBER_SIGN: return '#';
        case IC_3: return '3';
        case IC_DOLLAR_SIGN: return '$';
        case IC_4: return '4';
        case IC_PERCENT_SIGN: return '%';
        case IC_5: return '5';
        case IC_CARET: return '^';
        case IC_6: return '6';
        case IC_AMPERSAND: return '&';
        case IC_7: return '7';
        case IC_ASTERISK: return '*';
        case IC_8: return '8';
        case IC_LEFT_PAREN: return '(';
        case IC_9: return '9';
        case IC_RIGHT_PAREN: return ')';
        case IC_0: return '0';
        case IC_UNDERSCORE: return '_';
        case IC_HYPHEN_MINUS: return '-';
        case IC_PLUS_SIGN: return '+';
        case IC_EQUAL_SIGN: return '=';
        case IC_BACKSPACE: return '\b';

        case IC_TAB: return '\t';
        case IC_Q: return 'q';
        case IC_W: return 'w';
        case IC_E: return 'e';
        case IC_R: return 'r';
        case IC_T: return 't';
        case IC_Y: return 'y';
        case IC_U: return 'u';
        case IC_I: return 'i';
        case IC_O: return 'o';
        case IC_P: return 'p';
        case IC_LEFT_CURLY_BRACE: return '{';
        case IC_LEFT_SQUARE_BRACKET: return '[';
        case IC_RIGHT_CURLY_BRACE: return '}';
        case IC_RIGHT_SQUARE_BRACKET: return ']';
        case IC_PIPE: return '|';
        case IC_BACKSLASH: return '\\';

        case IC_A: return 'a';
        case IC_S: return 's';
        case IC_D: return 'd';
        case IC_F: return 'f';
        case IC_G: return 'g';
        case IC_H: return 'h';
        case IC_J: return 'j';
        case IC_K: return 'k';
        case IC_L: return 'l';
        case IC_COLON: return ':';
        case IC_SEMICOLON: return ';';
        case IC_DOUBLE_QUOTE: return '"';
        case IC_APOSTROPHE: return '\'';
        case IC_ENTER: return '\n';

        case IC_Z: return 'z';
        case IC_X: return 'x';
        case IC_C: return 'c';
        case IC_V: return 'v';
        case IC_B: return 'b';
        case IC_N: return 'n';
        case IC_M: return 'm';
        case IC_LESS_THAN_SIGN: return '<';
        case IC_COMMA: return ',';
        case IC_GREATER_THAN_SIGN: return '>';
        case IC_PERIOD: return '.';
        case IC_QUESTION_MARK: return '?';
        case IC_SLASH: return '/';
        case IC_SPACE: return ' ';

        default:
            return '\0';
    }
}


char InputCode::upperForOrdinal(int ic)
{
    switch (ic) {
        case IC_TILDE: return '~';
        case IC_GRAVE: return '`';
        case IC_EXCLAMATION_MARK: return '!';
        case IC_1: return '1';
        case IC_AT_SIGN: return '@';
        case IC_2: return '2';
        case IC_NUMBER_SIGN: return '#';
        case IC_3: return '3';
        case IC_DOLLAR_SIGN: return '$';
        case IC_4: return '4';
        case IC_PERCENT_SIGN: return '%';
        case IC_5: return '5';
        case IC_CARET: return '^';
        case IC_6: return '6';
        case IC_AMPERSAND: return '&';
        case IC_7: return '7';
        case IC_ASTERISK: return '*';
        case IC_8: return '8';
        case IC_LEFT_PAREN: return '(';
        case IC_9: return '9';
        case IC_RIGHT_PAREN: return ')';
        case IC_0: return '0';
        case IC_UNDERSCORE: return '_';
        case IC_HYPHEN_MINUS: return '-';
        case IC_PLUS_SIGN: return '+';
        case IC_EQUAL_SIGN: return '=';
        case IC_BACKSPACE: return '\b';

        case IC_TAB: return '\t';
        case IC_Q: return 'Q';
        case IC_W: return 'W';
        case IC_E: return 'E';
        case IC_R: return 'R';
        case IC_T: return 'T';
        case IC_Y: return 'Y';
        case IC_U: return 'U';
        case IC_I: return 'I';
        case IC_O: return 'O';
        case IC_P: return 'P';
        case IC_LEFT_CURLY_BRACE: return '{';
        case IC_LEFT_SQUARE_BRACKET: return '[';
        case IC_RIGHT_CURLY_BRACE: return '}';
        case IC_RIGHT_SQUARE_BRACKET: return ']';
        case IC_PIPE: return '|';
        case IC_BACKSLASH: return '\\';

        case IC_A: return 'A';
        case IC_S: return 'S';
        case IC_D: return 'D';
        case IC_F: return 'F';
        case IC_G: return 'G';
        case IC_H: return 'H';
        case IC_J: return 'J';
        case IC_K: return 'K';
        case IC_L: return 'L';
        case IC_COLON: return ':';
        case IC_SEMICOLON: return ';';
        case IC_DOUBLE_QUOTE: return '"';
        case IC_APOSTROPHE: return '\'';
        case IC_ENTER: return '\n';

        case IC_Z: return 'Z';
        case IC_X: return 'X';
        case IC_C: return 'C';
        case IC_V: return 'V';
        case IC_B: return 'B';
        case IC_N: return 'N';
        case IC_M: return 'M';
        case IC_LESS_THAN_SIGN: return '<';
        case IC_COMMA: return ',';
        case IC_GREATER_THAN_SIGN: return '>';
        case IC_PERIOD: return '.';
        case IC_QUESTION_MARK: return '?';
        case IC_SLASH: return '/';
        case IC_SPACE: return ' ';

        default:
            return '\0';
    }
}

} } }

