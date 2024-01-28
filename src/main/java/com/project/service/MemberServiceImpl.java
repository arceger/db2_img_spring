package com.project.service;

import com.project.model.Member;
import com.project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository imageRepository;

    @Override
    public Member create(Member image) {
        return imageRepository.save(image);
    }

    @Override
    public List<Member> viewAll() {
        return (List<Member>) imageRepository.findAll();
    }

    @Override
    public Member viewById(long id) {
        return imageRepository.findById(id).get();
    }
    @Override
    public Member oneById(long id) {
        return imageRepository.findById(id).orElse(null);
    }
//    @Override
//    public MemberDetails viewById(long id) throws SQLException {
//        Member member = imageRepository.findById(id).orElse(null);
//
//        if (member != null) {
//            byte[] imageBytes = member.getImage().getBytes(1, (int) member.getImage().length());
//            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//
//            return new MemberDetails(
//                    member.getId(),
//                    member.getNome(),
//                    member.getSobrenome(),
//                    member.getFamilia(),
//                    base64Image,
//                    member.getData_admissao(),
//                    member.getData_batismo(),
//                    member.getEndereco(),
//                    member.getContato(),
//                    member.getObservacoes()
//            );
//        } else {
//            return null;
//        }
//    }

}
