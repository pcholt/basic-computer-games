public class AwariGame {
    public static void main(String[] args) {
        new AwariBuilder()
                .ai(new AwariRandomAi(7))
                .build()
                .start();
    }
}
