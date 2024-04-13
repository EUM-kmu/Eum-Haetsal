package com.eum.haetsal.service;

import com.eum.haetsal.domain.block.Block;
import com.eum.haetsal.domain.block.BlockRepository;
import com.eum.haetsal.domain.profile.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;

    /**
     * 차단하기
     * @param blocker 차단하는 유저
     * @param blocked 처덩덩헌 유저
     * @return
     */
    public void blockedAction(Profile blocker,Profile blocked){
        if(blocker == blocked) throw new IllegalArgumentException("자기자신을 차단할수 없습니다");
        if(blockRepository.existsByBlockerAndBlocked(blocker,blocked))
            throw new IllegalArgumentException("이미 차단한 유저 입니다");
        Block block = Block.toEntity(blocker, blocked);
        blockRepository.save(block);

    }
    public void deleteBlockedAction(Profile blocker,Profile blocked){
            Block block = blockRepository.findByBlockerAndBlocked(blocker, blocked).orElseThrow(()-> new IllegalArgumentException("해당 유저는 차단한적이 없습니다"));
            blockRepository.delete(block);

    }
    public List<Profile> getBlockedUser(Profile getProfile){
        List<Profile> blockedUsers = new ArrayList<>();
//        내가 차단한 사람들
        List<Block> blockers = blockRepository.findByBlocker(getProfile).orElse(Collections.emptyList());
        for (Block block:blockers){
            blockedUsers.add(block.getBlocked());
        }
//        날 차단힌 시림들
        List<Block> blocked = blockRepository.findByBlocked(getProfile).orElse(Collections.emptyList());
        for(Block block:blocked){
            blockedUsers.add(block.getBlocker());
        }
        return blockedUsers;
    }
    public List<Long> getMyBlockedIds(Profile profile){
        List<Block> blockers = blockRepository.findByBlocker(profile).orElse(Collections.emptyList());
        List<Long> blockedUserIds = new ArrayList<>();
        for (Block block:blockers){
            blockedUserIds.add(block.getBlocked().getUser().getUserId());
        }
        return blockedUserIds;
    }
    public Boolean isBlocked(Profile blocker, Profile blocked){
        return blockRepository.existsByBlockerAndBlocked(blocker, blocked);
    }
}
