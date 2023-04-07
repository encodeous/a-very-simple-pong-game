package ca.encodeous.pong.system;

import ca.encodeous.pong.Constants;
import ca.encodeous.pong.physics.GameObject;
import ca.encodeous.pong.physics.Vec2d;
import ca.encodeous.pong.rendering.BoxObject;

/**
 * MIT License
 *
 * Copyright (c) 2023 Adam Chen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class Paddle extends BoxObject {
    // Add any state variables here

    /**
     * Fill in this method with code that describes the behavior
     * of a paddle from one moment to the next
     */
    public void act() {
        // add more friction to the paddles
        setVelocity(getVelocity().multiply(Constants.PADDLE_DRAG));
    }

    @Override
    public boolean onCollideWith(GameObject other) {
        setVelocity(new Vec2d(0, 0));
        return true;
    }
}