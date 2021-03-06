// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2020
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.controller.maschine.command.trigger;

import de.mossgrabers.controller.maschine.MaschineConfiguration;
import de.mossgrabers.controller.maschine.controller.MaschineControlSurface;
import de.mossgrabers.framework.command.trigger.mode.ModeMultiSelectCommand;
import de.mossgrabers.framework.daw.IModel;
import de.mossgrabers.framework.daw.data.ISend;
import de.mossgrabers.framework.daw.data.ITrack;
import de.mossgrabers.framework.daw.data.bank.ISendBank;
import de.mossgrabers.framework.mode.ModeManager;
import de.mossgrabers.framework.mode.Modes;
import de.mossgrabers.framework.utils.ButtonEvent;


/**
 * Selects the previous/next send mode.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class MaschineSendSelectCommand extends ModeMultiSelectCommand<MaschineControlSurface, MaschineConfiguration>
{
    /**
     * Constructor.
     *
     * @param model The model
     * @param surface The surface
     */
    public MaschineSendSelectCommand (final IModel model, final MaschineControlSurface surface)
    {
        super (model, surface, Modes.SEND1, Modes.SEND2, Modes.SEND3, Modes.SEND4, Modes.SEND5, Modes.SEND6, Modes.SEND7, Modes.SEND8);
    }


    /** {@inheritDoc} */
    @Override
    public void executeShifted (final ButtonEvent event)
    {
        final ModeManager modeManager = this.surface.getModeManager ();
        final Modes activeMode = modeManager.getActiveModeId ();

        super.executeShifted (event);

        final Modes newMode = modeManager.getActiveModeId ();
        if (activeMode == newMode)
            return;

        final int sendIndex = newMode.ordinal () - Modes.SEND1.ordinal ();

        final ITrack t = this.model.getCurrentTrackBank ().getSelectedItem ();
        if (t == null)
            return;
        final ISendBank sendBank = t.getSendBank ();
        final ISend send = sendBank.getItem (sendIndex);
        if (send.doesExist ())
            this.surface.getDisplay ().notify ("Send " + (sendIndex + 1) + ": " + send.getName ());
    }


    /** {@inheritDoc} */
    @Override
    public void executeNormal (final ButtonEvent event)
    {
        final ModeManager modeManager = this.surface.getModeManager ();
        final Modes activeMode = modeManager.getActiveModeId ();

        super.executeNormal (event);

        final Modes newMode = modeManager.getActiveModeId ();
        if (activeMode == newMode)
            return;

        final int sendIndex = newMode.ordinal () - Modes.SEND1.ordinal ();

        final ITrack t = this.model.getCurrentTrackBank ().getSelectedItem ();
        if (t == null)
            return;
        final ISendBank sendBank = t.getSendBank ();
        final ISend send = sendBank.getItem (sendIndex);
        if (send.doesExist ())
            this.surface.getDisplay ().notify ("Send " + (sendIndex + 1) + ": " + send.getName ());
    }
}
