package com.gempukku.swccgo.logic.modifiers;

import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;

/**
 * A modifier that causes Plagueis Probe Droids to not be able to be deployed or moved to same location
 * as another Plagueis Probe Droid
 */
public class MayNotDeployOrMovePlagueisProbeDroidToLocationsModifier extends AbstractModifier {

    /**
     * Creates a modifier that causes affected Plagueis Probe Droids to not be able to be deployed or moved to same location
     * as another Plagueis Probe Droid
     * @param source the source of the modifier
     */

    public MayNotDeployOrMovePlagueisProbeDroidToLocationsModifier(PhysicalCard source) {
        super(source, "May not deploy or move to same location as another Sith Probe Droid", Filters.Plagueis_Probe_Droid, null, ModifierType.MAY_NOT_DEPLOY_MOVE_PLAGUEIS_PROBE_DROID);
    }
}