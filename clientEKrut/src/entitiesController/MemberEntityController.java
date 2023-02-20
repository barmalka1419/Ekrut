package entitiesController;

import entities.Member;

/**
 * The Class MemberEntityController.
 * class for storing a member's information
 */
public class MemberEntityController {
	
	private static Member member;

	
	/**
	 * Gets the member.
	 *
	 * @return the member
	 */
	public static Member getMember() {
		  return member;
	}
	
	/**
	 * Sets the member.
	 *
	 * @param memberDB the new member
	 */
	public void setMember(Member memberDB) {
		member = memberDB;
	}
	

}
