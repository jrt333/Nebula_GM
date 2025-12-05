package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.AgentRewardReceive.AgentRewardReceiveReq;
import emu.nebula.proto.AgentRewardReceive.AgentRewardReceiveResp;
import emu.nebula.proto.AgentRewardReceive.AgentRewardShow;
import emu.nebula.net.HandlerId;

import java.util.ArrayList;

import emu.nebula.game.agent.AgentResult;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.agent_reward_receive_req)
public class HandlerAgentRewardReceiveReq extends NetHandler {

    @Override
    @SuppressWarnings("unchecked")
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse request
        var req = AgentRewardReceiveReq.parseFrom(message);
        
        // Claim
        var change = session.getPlayer().getAgentManager().claim(req.getId());
        
        if (change == null) {
            return session.encodeMsg(NetMsgId.agent_reward_receive_failed_ack);
        }
        
        // Build response
        var rsp = AgentRewardReceiveResp.newInstance()
                .setChange(change.toProto());
        
        // Handle results
        var results = (ArrayList<AgentResult>) change.getExtraData();
        
        for (var result : results) {
            // Add char ids
            rsp.addAllCharIds(result.getAgent().getCharIds());
            
            // Agent rewards proto
            var show = AgentRewardShow.newInstance()
                    .setId(result.getAgent().getId());
            
            result.getRewards().toItemTemplateStream().forEach(show::addRewards);
            result.getBonus().toItemTemplateStream().forEach(show::addBonus);
            
            rsp.addRewardShows(show);
        }
        
        // Encode and send
        return session.encodeMsg(NetMsgId.agent_reward_receive_succeed_ack, rsp);
    }

}
