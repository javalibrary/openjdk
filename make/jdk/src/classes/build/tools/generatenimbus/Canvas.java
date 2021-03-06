/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package build.tools.generatenimbus;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

class Canvas {
    private Dimension size;

    public Dimension getSize() { return size; }

    private List<Layer> layers;
    public List<Layer> getLayers() { return layers; }

    private Insets stretchingInsets = null;

    Canvas(XMLStreamReader reader) throws XMLStreamException {
        layers = new ArrayList<>();
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "size":
                            size = new Dimension(reader);
                            break;
                        case "layer":
                            layers.add(new Layer(reader));
                            break;
                        case "stretchingInsets":
                            stretchingInsets = new Insets(reader);
                            break;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "canvas":
                            return;
                    }
                    break;
            }
        }
    }

    public Insets getStretchingInsets() { return stretchingInsets; }

    public boolean isBlank() {
        return layers.size() == 0 || (layers.size() == 1 && layers.get(0).isEmpty());
    }
}
