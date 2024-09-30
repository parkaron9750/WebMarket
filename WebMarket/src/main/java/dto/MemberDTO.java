package dto;

public class MemberDTO {
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberBirth;
	private String memberGender;
	private String memberEmail;
	
	public MemberDTO() {

	}
	
	public MemberDTO(String memberId, String memberPw, String memberName, String memberBirth, String memberGender,
			String memberEmail) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.memberBirth = memberBirth;
		this.memberGender = memberGender;
		this.memberEmail = memberEmail;
	}

	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberBirth() {
		return memberBirth;
	}
	public void setMemberBirth(String memberBirth) {
		this.memberBirth = memberBirth;
	}
	public String getMemberGender() {
		return memberGender;
	}
	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	
}
