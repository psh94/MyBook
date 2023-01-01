package com.psh.mybook.service;

import com.psh.mybook.mapper.ReplyMapper;
import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.Page;
import com.psh.mybook.model.reply.ReplyEnrollParam;
import com.psh.mybook.model.reply.ReplyPage;
import com.psh.mybook.model.reply.ReplyUpdateParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyMapper replyMapper;


    @Override
    public int enrollReply(ReplyEnrollParam param) {
        return replyMapper.enrollReply(param);
    }

    @Override
    public ReplyPage replyList(Criteria cri) {
        // reply가 list로 있는 객체 생성
        ReplyPage replyPage = new ReplyPage();

        // bookId를 통해 reply 정보 추출,
        replyPage.setList(replyMapper.getReplyList(cri));
        // cri에서 얻은 book의 정보로 reply의 전체 개수를 얻어 페이지 구성
        replyPage.setPageInfo(new Page(cri, replyMapper.getReplyTotal(cri.getBookId())));

        // reply 전체를 나타내는 replyPage 반환
        return replyPage;
    }

    @Override
    public int updateReply(ReplyUpdateParam param) {
        return replyMapper.updateReply(param);
    }


    @Override
    public int deleteReply(int replyId) {

        return replyMapper.deleteReply(replyId);
    }
}
