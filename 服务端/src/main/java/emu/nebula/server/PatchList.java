package emu.nebula.server;

import java.util.List;

import emu.nebula.proto.Pb.ClientDiff;
import emu.nebula.proto.Pb.FileDiff;
import lombok.Getter;

@Getter
public class PatchList {
    public long version;
    public List<PatchListFile> files;
    
    @Getter
    public static class PatchListFile {
        public String name;
        public String hash;
        public long version;
        public String additionalPath;
    }
    
    // Proto
    
    public ClientDiff toProto() {
        var proto = ClientDiff.newInstance();
        
        for (var file : files) {
            var diff = FileDiff.newInstance()
                    .setFileName(file.getName())
                    .setHash(file.getHash())
                    .setVersion(this.getVersion())
                    .setAdditionalPath(file.getAdditionalPath());
            
            proto.addDiff(diff);
        }
        
        return proto;
    }
}
