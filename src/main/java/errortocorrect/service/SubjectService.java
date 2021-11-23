package errortocorrect.service;


import errortocorrect.dto.ReplyDto;
import errortocorrect.dto.SubjectDto;
import errortocorrect.entity.Subject;
import errortocorrect.entity.User;
import errortocorrect.repository.SubjectRepository;
import errortocorrect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    UserRepository userRepository;

    public List<Subject> subjectList() {
        return subjectRepository.findAll();
    }

    public Subject createSubject(SubjectDto subjectDto, Long uid) {
        User user = userRepository.findById(uid).get();
        Subject subject = new Subject();
        subject.setTitle(subjectDto.getTitle());
        subject.setContent(subjectDto.getContent());
        subject.setUser(user);
        subject.setGoodOrElse(0);
        subject.setHotOrElse(0);
        subject.setTopOrElse(0);
        subject.setRecommendOrElse(0);
        subject.setThumbNum(0);
        subject.setViewNum(0);
        subject.setReplyNum(0);
        return subjectRepository.save(subject);
    }

    public void viewNumPlusPlus(Long subjectId){
        Subject subject = subjectRepository.findById(subjectId);
        subject.setViewNum(subject.getViewNum()+1);
        subjectRepository.save(subject);
    }


    public List<Subject> mySubject(Long uid) {
        List<Subject> subjects = subjectRepository.findByUser_IdAndParentSubjectIsNullOrderByCreateTimeDesc(uid);
        Collections.sort(subjects, (o1, o2) -> o1.getTopOrElse() < o2.getTopOrElse()?1:((o1.getTopOrElse() == o2.getTopOrElse())?0:-1));
        return subjects;
    }

    public Subject subjectDetail(SubjectDto subjectDto) {
        Subject subject = subjectRepository.findById(subjectDto.getSubjectId());
        subject.setViewNum(subject.getViewNum()+1);
        subject.getSubSubjects().stream().sorted(Comparator.comparing(Subject::getCreateTime));
        subjectRepository.save(subject);
        return subject;
    }

    public List<Subject> subjectListHottest() {
        List<Subject> subjects = subjectRepository.findByParentSubjectIsNullOrderByThumbNumDescReplyNumDescViewNumDesc();
        Collections.sort(subjects, (o1, o2) -> o1.getTopOrElse() < o2.getTopOrElse()?1:((o1.getTopOrElse() == o2.getTopOrElse())?0:-1));
        return subjects;
    }

    public List<Subject> subjectListLatest() {
        List<Subject> subjects = subjectRepository.findByParentSubjectIsNullOrderByCreateTimeDesc();
        Collections.sort(subjects, (o1, o2) -> o1.getTopOrElse() < o2.getTopOrElse()?1:((o1.getTopOrElse() == o2.getTopOrElse())?0:-1));
        return subjects;
    }

    public List<Subject> subjectListLatestReply() {
        return subjectRepository.findByParentSubjectIsNullOrderByCreateTimeDesc();
    }

    public Integer thumbSubject(SubjectDto subjectDto) {
        Subject subject = subjectRepository.findById(subjectDto.getSubjectId());
        subject.setThumbNum(subject.getThumbNum()+1);
        subjectRepository.save(subject);
        return subject.getThumbNum();
    }

    public Integer thumbDownDownSubject(SubjectDto subjectDto) {
        Subject subject = subjectRepository.findById(subjectDto.getSubjectId());
        subject.setThumbNum(subject.getThumbNum()-1);
        subjectRepository.save(subject);
        return subject.getThumbNum();
    }

    public Subject replySubject(ReplyDto replyDto) {
        Subject subject = subjectRepository.findById(replyDto.getSubjectId());
        User user = userRepository.findById(replyDto.getUserId()).get();
        Subject sub = new Subject();
        sub.setContent(replyDto.getReplyContent());
        sub.setUser(user);
        sub.setGoodOrElse(0);
        sub.setHotOrElse(0);
        sub.setTopOrElse(0);
        sub.setRecommendOrElse(0);
        sub.setThumbNum(0);
        sub.setViewNum(0);
        sub.setReplyNum(0);
        sub.setParentSubject(subject);
        subject.addSubSubjects(sub);
        subject.setReplyNum(subject.getReplyNum()+1);
        subjectRepository.save(subject);
        return subjectRepository.save(sub);
    }

    public void deletesSubject(SubjectDto subjectDto) {
        Subject subject = subjectRepository.findById(subjectDto.getSubjectId());
        subjectRepository.delete(subject);
    }


}
