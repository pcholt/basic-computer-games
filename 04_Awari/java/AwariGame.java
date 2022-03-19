public class AwariGame {
    public static void main(String[] args) {
        Awari awari = new AwariBuilder()
                .ai(new AwariRandomAi(7))
                .build();
    }
}
