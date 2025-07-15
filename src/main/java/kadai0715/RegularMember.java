package kadai0715;

// 一般会員：借りられる冊数は最大3冊
public class RegularMember extends Member {
    public RegularMember(String memberId, String name, String email) {
        super(memberId, name, email);
    }

    @Override
    public int getMaxBorrowCount() {
        return 3;
    }

    @Override
    public String getMemberType() {
        return "一般会員";
    }
}
