package errortocorrect.entity;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "testcase")
@EntityListeners(AuditingEntityListener.class)
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "input")
    private String  input;

    @Column(name = "output")
    private String  output;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "puzzle_id")
    private Puzzle puzzle;

    public Long getId() {
        return id;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }
}
