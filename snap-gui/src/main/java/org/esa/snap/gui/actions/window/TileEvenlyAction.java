/*
 * Copyright (C) 2014 Brockmann Consult GmbH (info@brockmann-consult.de)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see http://www.gnu.org/licenses/
 */
package org.esa.snap.gui.actions.window;

import org.esa.snap.gui.util.Tileable;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * The "Tile Evenly" action.
 *
 * @author Norman Fomferra
 */
@ActionID(
        category = "Window",
        id = "org.esa.snap.gui.temp.TileEvenlyAction"
)
@ActionReference(
        path = "Toolbars/Window",
        position = 20
)
@ActionRegistration(
        displayName = "#CTL_TileEvenlyActionName",
        iconBase = "org/esa/snap/gui/icons/TileEvenly22.png"
)
@NbBundle.Messages("CTL_TileEvenlyActionName=Tile Evenly")
public class TileEvenlyAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        Tileable tileable = Utilities.actionsGlobalContext().lookup(Tileable.class);
        if (tileable == null) {
            tileable = new TileableImpl();
        }
        tileable.tileEvenly();
    }
}
