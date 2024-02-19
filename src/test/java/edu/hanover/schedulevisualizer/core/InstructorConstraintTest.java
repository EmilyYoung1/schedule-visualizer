package edu.hanover.schedulevisualizer.core;

import edu.hanover.schedulevisualizer.conflict.InstructorConflict;
import edu.hanover.schedulevisualizer.conflict.InstructorConstraint;
import edu.hanover.schedulevisualizer.core.entity.Instructor;
import edu.hanover.schedulevisualizer.core.entity.Section;
import edu.hanover.schedulevisualizer.core.entity.Weekday;
import edu.hanover.schedulevisualizer.core.simpleEntity.SimpleSection;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InstructorConstraintTest extends ContextAwareTest {
    @Test
    public void TimeSlotIsFilled(){
        Section section1 = ef.makeSection(ef.makeCourse("CS", "321", "Software Development Practicum"), ef.makeHCTimeSlot(Weekday.MWF(), 1));
        SimpleSection section2 = ef.makeSection(ef.makeCourse("CS", "321", "Software Development Practicum"), ef.makeHCTimeSlot(Weekday.MWF(), 1));
        Instructor instructor1 = ef.makeInstructor("John", "Smith", "456357");
        section1.addInstructor(instructor1);
        section2.addInstructor(instructor1);
        InstructorConstraint FilledTimeSlot = new InstructorConstraint();
        assertTrue(FilledTimeSlot.twoConflictingCourses(section1, section2));

    }

    @Test
    public void TimeSlotIsNotFilled() {
        Section section1 = ef.makeSection(ef.makeCourse("CS", "321", "Software Development Practicum"), ef.makeHCTimeSlot(Weekday.MWF(), 1));
        SimpleSection section2 = ef.makeSection(ef.makeCourse("CS", "321", "Software Development Practicum"), ef.makeHCTimeSlot(Weekday.MWF(), 1));
        Instructor instructor1 = ef.makeInstructor("John", "Smith", "456357");
        Instructor instructor2 = ef.makeInstructor("Colin", "Sharp", "456857");
        section1.addInstructor(instructor1);
        section2.addInstructor(instructor2);
        InstructorConstraint IsntFilledTimeSlot = new InstructorConstraint();
        assertFalse(IsntFilledTimeSlot.twoConflictingCourses(section1, section2));
    }

    @Test
    public void neitherCourseHasInstructor() {
        Section section1 = ef.makeSection(ef.makeCourse("CS", "321", "Software Development Practicum"), ef.makeHCTimeSlot(Weekday.MWF(), 1));
        SimpleSection section2 = ef.makeSection(ef.makeCourse("CS", "321", "Software Development Practicum"), ef.makeHCTimeSlot(Weekday.MWF(), 1));
        InstructorConstraint IsntFilledTimeSlot = new InstructorConstraint();
        assertFalse(IsntFilledTimeSlot.twoConflictingCourses(section1, section2));
    }

    @Test
    public void twoSectionsHaveSameInstructors() {
        Section section1 = ef.makeSection(ef.makeCourse("CS", "321", "Software Development Practicum"), ef.makeHCTimeSlot(Weekday.MWF(), 1));
        SimpleSection section2 = ef.makeSection(ef.makeCourse("CS", "321", "Software Development Practicum"), ef.makeHCTimeSlot(Weekday.MWF(), 1));
        Instructor instructor1 = ef.makeInstructor("John", "Smith", "456357");
        section1.addInstructor(instructor1);
        section2.addInstructor(instructor1);
        InstructorConstraint instructorconstraint = new InstructorConstraint();
        assertTrue(instructorconstraint.generateConflict(section1, section2).equals(Optional.of(new InstructorConflict(section1, section2))));
    }

    @Test
    public void twoSectionsHaveDifferentInstructors() {
        Section section1 = ef.makeSection(ef.makeCourse("CS", "321", "Software Development Practicum"), ef.makeHCTimeSlot(Weekday.MWF(), 1));
        SimpleSection section2 = ef.makeSection(ef.makeCourse("CS", "321", "Software Development Practicum"), ef.makeHCTimeSlot(Weekday.MWF(), 1));
        Instructor instructor1 = ef.makeInstructor("John", "Smith", "456357");
        Instructor instructor2 = ef.makeInstructor("Colin", "Sharp", "456857");
        section1.addInstructor(instructor1);
        section2.addInstructor(instructor2);
        InstructorConstraint instructorconstraint = new InstructorConstraint();
        assertEquals(instructorconstraint.generateConflict(section1, section2), Optional.empty());
    }
}
