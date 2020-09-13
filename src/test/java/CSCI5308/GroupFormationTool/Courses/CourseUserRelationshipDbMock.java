package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.AccessControl.User;

import java.util.*;


class CourseUserRelationshipDbMock implements ICourseUserRelationshipPersistence {

	private Map<Long, Map<Role, List<IUser>>> courseRoleMock = new HashMap<>();
	private Map<Role, List<IUser>> roleUserMock1 = new HashMap<>();
	private Map<Role, List<IUser>> roleUserMock2 = new HashMap<>();
	private Map<Role, List<IUser>> roleUserMock3 = new HashMap<>();

	public CourseUserRelationshipDbMock() {

		IUser user = new User();
		user.setBannerId("B00001");
		user.setFirstName("Akshay");
		user.setLastName("Singh");
		user.setId(4);
		user.setEmail("akki007.singh@gmail.com");
		user.setPassword("12345");

		IUser user1 = new User();
		user1.setBannerId("B00002");
		user1.setFirstName("Deepak");
		user1.setLastName("Shaw");
		user1.setId(1);
		user1.setEmail("deepal.shaw@gmail.com");
		user1.setPassword("12345");

		IUser user2 = new User();
		user2.setBannerId("B00003");
		user2.setFirstName("Amit");
		user2.setLastName("Gupta");
		user2.setId(2);
		user2.setEmail("amit.singh@gupta.com");
		user2.setPassword("12345");

		List<IUser> userList1 = new ArrayList<>();
		List<IUser> userList2 = new ArrayList<>();
		List<IUser> userList3 = new ArrayList<>();

		userList1.add(user);
		userList2.add(user1);
		userList3.add(user2);

		roleUserMock1.put(Role.ADMIN, userList1);
		roleUserMock2.put(Role.INSTRUCTOR, userList2);
		roleUserMock3.put(Role.STUDENT, userList3);

		courseRoleMock.put(5408L, roleUserMock1);
		courseRoleMock.put(5409L, roleUserMock2);
		courseRoleMock.put(5417L, roleUserMock1);
		courseRoleMock.put(5418L, roleUserMock1);
	}

	@Override
	public List<IUser> findAllUsersWithoutCourseRole(Role role, long courseId) {
		return courseRoleMock.get(courseId).get(role);
	}

	@Override
	public List<IUser> findAllUsersWithCourseRole(Role role, long courseId) {
		return courseRoleMock.get(courseId).get(role);
	}

	@Override
	public boolean enrollUser(ICourse course, IUser user, Role role) {
		List<IUser> userList = new ArrayList<>();
		userList.add(user);
		roleUserMock1.put(role, userList);
		courseRoleMock.put(course.getId(), roleUserMock1);

		roleUserMock2.put(role, userList);
		courseRoleMock.put(course.getId(), roleUserMock2);

		roleUserMock3.put(role, userList);
		courseRoleMock.put(course.getId(), roleUserMock3);

		if (!courseRoleMock.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public List<Role> loadUserRolesForCourse(ICourse course, IUser user) {
		Set<Role> roles = courseRoleMock.get(course.getId()).keySet();
		List<Role> roleList = new ArrayList<>();
		roleList.addAll(roles);
		return roleList;
	}
}