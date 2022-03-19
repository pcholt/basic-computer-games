public class AwariBuilder {

    private AwariAi awariAi;

    public AwariBuilder ai(AwariAi awariAi) {
        this.awariAi = awariAi;
        return this;
    }

    public Awari build() {
        return new Awari(awariAi);
    }
}
