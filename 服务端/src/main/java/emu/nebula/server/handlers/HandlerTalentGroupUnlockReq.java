package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.Public.UI32;
import emu.nebula.proto.TalentGroupUnlock.TalentGroupUnlockResp;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import emu.nebula.net.HandlerId;
import emu.nebula.data.GameData;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.talent_group_unlock_req)
public class HandlerTalentGroupUnlockReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse request
        var req = UI32.parseFrom(message);
        
        // Get talent group data
        var talentGroup = GameData.getTalentGroupDataTable().get(req.getValue());
        if (talentGroup == null) {
            return session.encodeMsg(NetMsgId.talent_group_unlock_failed_ack);
        }
        
        // Get character
        var character = session.getPlayer().getCharacters().getCharacterById(talentGroup.getCharId());
        if (character == null) {
            return session.encodeMsg(NetMsgId.talent_group_unlock_failed_ack);
        }
        
        // Unlock talent
        var changes = character.unlockTalent(talentGroup);
        
        if (changes == null) {
            return session.encodeMsg(NetMsgId.talent_group_unlock_failed_ack);
        }
        
        // Build response
        var rsp = TalentGroupUnlockResp.newInstance()
                .setChange(changes.toProto());
        
        if (changes.getExtraData() != null) {
            var nodes = (IntArrayList) changes.getExtraData();
            nodes.forEach(rsp::addNodes);
        }
        
        // Encode response
        return session.encodeMsg(NetMsgId.talent_group_unlock_succeed_ack, rsp);
    }

}
