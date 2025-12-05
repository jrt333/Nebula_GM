package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.AgentApply.AgentApplyReq;
import emu.nebula.proto.AgentApply.AgentApplyResp;
import emu.nebula.proto.AgentApply.AgentRespInfo;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.agent_apply_req)
public class HandlerAgentApplyReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse request
        var req = AgentApplyReq.parseFrom(message);
        
        // Build response
        var rsp = AgentApplyResp.newInstance();
        
        // Apply for commissions
        for (var apply : req.getApply()) {
            // Apply
            var agent = session.getPlayer().getAgentManager().apply(
                    apply.getId(),
                    apply.getProcessTime(),
                    apply.getCharIds()
            );
            
            // Serialize to proto
            var info = AgentRespInfo.newInstance()
                    .setId(agent.getId())
                    .setBeginTime(agent.getStart());
            
            rsp.addInfos(info);
        }
        
        // Save
        if (rsp.getInfos().length() > 0) {
            session.getPlayer().getAgentManager().save();
        }
        
        // Encode and send
        return session.encodeMsg(NetMsgId.agent_apply_succeed_ack, rsp);
    }

}
