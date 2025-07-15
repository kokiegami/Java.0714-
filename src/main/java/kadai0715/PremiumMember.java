package kadai0715;

// プレミアム会員：借りられる冊数は最大10冊
public class PremiumMember extends Member {
    public PremiumMember(String memberId, String name, String email) {
        super(memberId, name, email);
    }

    @Override
    public int getMaxBorrowCount() {
        return 10;
    }

    @Override
    public String getMemberType() {
        return "プレミアム会員";
    }
}
